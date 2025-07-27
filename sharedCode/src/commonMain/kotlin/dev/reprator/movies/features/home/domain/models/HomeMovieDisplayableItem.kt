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

data class HomeMovieDisplayableItem(
    override val id: String,
    override val typeId: String,
    override val name: String,
    override val description: String,
    override val ratings: String,
    override val posterImage: String,
    override val language: String,
    override val categoriesList: List<String>,
    override val categoryType: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_MOVIE,
    val watchLink: String,
    val movieSize: String,
    val movieRuntime: String,
    val castAndCrew: String,
) : CategoryDisplayableItem

data class HomeEpisodeOverView(
    override val id: String,
    override val typeId: String,
    override val name: String,
    override val description: String,
    override val ratings: String,
    override val posterImage: String,
    override val language: String,
    override val categoriesList: List<String>,
    override val categoryType: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_TV,
) : CategoryDisplayableItem

data class MovieGenreItem(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)
