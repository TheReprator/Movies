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

package dev.reprator.movies.features.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.reprator.movies.util.base.mvi.MVI
import dev.reprator.movies.util.base.mvi.Middleware
import dev.reprator.movies.util.base.mvi.Reducer
import dev.reprator.movies.util.base.mvi.mvi
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import kotlinx.coroutines.FlowPreview
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

const val SEARCH_QUERY_KEY = "searchQuery"
private const val MINIMUM_SEARCH_DEBOUNCE = 100L
private const val MINIMUM_SEARCH_LENGTH = 3

@OptIn(FlowPreview::class)
@Inject
class HomeViewModel(
    @Assisted val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppCoroutineDispatchers,
    private val middleWareList: Set<Middleware<HomeState, HomeAction, HomeEffect>>,
    private val reducer: Reducer<HomeState, HomeAction, HomeEffect>,
) : ViewModel(), MVI<HomeState, HomeAction, HomeEffect> by mvi(
    dispatchers,
    reducer,
    middleWareList,
    HomeState.initial()
) {
    override fun onCleared() {
        closeScope()
        super.onCleared()
    }
}
