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

import dev.reprator.movies.features.home.domain.models.DisplayableItem
import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeSectionModel
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.util.base.mvi.SideEffect
import dev.reprator.movies.util.base.mvi.UiAction
import dev.reprator.movies.util.base.mvi.UiState

sealed interface HomeAction : UiAction {
    object LoadHomeData : HomeAction

    data class SectionLoaded(
        val sectionType: HomeCategoryType,
        val items: List<DisplayableItem> = emptyList(),
        val genres: List<MovieGenreItem> = emptyList(),
    ) : HomeAction

    data class SectionLoadError(
        val sectionType: HomeCategoryType,
        val error: String,
    ) : HomeAction

    data class RetrySection(
        val sectionType: HomeCategoryType,
    ) : HomeAction

    data class CarouselPagination(
        val sectionType: HomeCategoryType,
    ) : HomeAction

    data class MovieGenreItemSelect(
        val genreItem: MovieGenreItem,
    ) : HomeAction
}

sealed interface HomeEffect : SideEffect {
    data class ShowError(
        val message: String,
    ) : HomeEffect
}

data class HomeState(
    val sections: List<HomeSectionModel>,
) : UiState {
    companion object {
        fun initial(): HomeState =
            HomeState(
                sections = emptyList(),
            )
    }
}
