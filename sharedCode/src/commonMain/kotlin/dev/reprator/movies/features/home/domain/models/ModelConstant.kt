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

package dev.reprator.movies.features.home.domain.models

enum class ResultStatus {
    RESULT_STATUS_LOADER,
    RESULT_STATUS_ERROR,
    RESULT_STATUS_EMPTY,
    RESULT_STATUS_RESULT,
}

enum class HomeCategoryType {
    HOME_CATEGORY_MOVIE,
    HOME_CATEGORY_TV,
    HOME_CATEGORY_GENRE,
}

enum class HomeSectionLayoutType {
    POSTER_CAROUSEL,
    GENRE_CHIPS,
}

sealed interface HomeSectionModel {
    val sectionId: HomeCategoryType
    val status: ResultStatus
    val layoutType: HomeSectionLayoutType

    data class ItemCarouselSection(
        val categoryName: String,
        val items: List<DisplayableItem>,
        override val sectionId: HomeCategoryType,
        override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
        override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.POSTER_CAROUSEL,
    ) : HomeSectionModel

    data class GenreChipsSection(
        val genres: List<MovieGenreItem>,
        override val sectionId: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_GENRE,
        override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
        override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.GENRE_CHIPS,
    ) : HomeSectionModel
}
