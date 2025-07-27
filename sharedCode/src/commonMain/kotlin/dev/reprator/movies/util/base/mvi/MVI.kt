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

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UiState

interface UiAction

interface SideEffect

@Stable
interface MVI<S : UiState, A : UiAction, E : SideEffect> {
    val uiState: StateFlow<S>
    val sideEffect: Flow<E>

    val currentState: S

    fun onAction(uiAction: A)

    fun updateUiState(block: S.() -> S)

    fun updateUiState(newUiState: S)

    fun CoroutineScope.emitSideEffect(effect: E)

    fun closeScope()
}

interface Reducer<S : UiState, A : UiAction, E : SideEffect> {
    fun reduce(
        previousState: S,
        action: A,
    ): Pair<S, E?>
}

typealias ActionDispatcher<A> = (A) -> Unit

interface Middleware<S : UiState, A : UiAction, E : SideEffect>: AutoCloseable {
    fun onAction(
        action: A,
        state: S,
    )

    fun attach(dispatcher: ActionDispatcher<A>)
}
