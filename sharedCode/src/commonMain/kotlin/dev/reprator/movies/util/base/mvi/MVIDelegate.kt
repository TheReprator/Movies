/*
 * Copyright 2025 @TheReprator
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.reprator.movies.util.base.mvi

import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MVIDelegate<S : UiState, A : UiAction, E : SideEffect> internal constructor(
    private val dispatcher: AppCoroutineDispatchers,
    private val reducer: Reducer<S, A, E>,
    private val middlewares: Set<Middleware<S, A, E>> = emptySet(),
    initialUiState: S,
) : MVI<S, A, E> {

    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _sideEffect = Channel<E>(Channel.BUFFERED)
    override val sideEffect: Flow<E> = _sideEffect.receiveAsFlow()

    init {
        middlewares.forEach { it.attach(::onAction) }
    }

    override val currentState: S get() = _uiState.value

    override fun onAction(uiAction: A) {
        val (newState, effect) = reducer.reduce(_uiState.value, uiAction)
        _uiState.value = newState
        effect?.let { _sideEffect.trySend(it) }

        middlewares.forEach { it.onAction(uiAction, newState) }
    }

    override fun updateUiState(block: S.() -> S) {
        _uiState.update(block)
    }

    override fun updateUiState(newUiState: S) {
        _uiState.value = newUiState
    }

    override fun CoroutineScope.emitSideEffect(effect: E) {
        launch(dispatcher.main) {
            _sideEffect.send(effect)
        }
    }

    override fun closeScope() {
        middlewares.forEach {
            it.close()
        }
    }
}

fun <S : UiState, A : UiAction, E : SideEffect> mvi(
    dispatcher: AppCoroutineDispatchers,
    reducer: Reducer<S, A, E>,
    middleware: Set<Middleware<S, A, E>> = emptySet(),
    initialUiState: S,
): MVI<S, A, E> = MVIDelegate(dispatcher, reducer, middleware, initialUiState)
