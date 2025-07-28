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

package dev.reprator.movies.features.home.data.repository.remote.model.movie

import dev.reprator.movies.features.home.data.repository.remote.model.tv.TvMapper.Companion.getPosterImageUrl
import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.util.api.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class MovieMapper : Mapper<ResponseModelMovie, HomeMovieDisplayableItem> {
    override suspend fun map(from: ResponseModelMovie): HomeMovieDisplayableItem =
        HomeMovieDisplayableItem(
            id = from.id.orEmpty(),
            typeId = from.movieID.orEmpty(),
            name = "${from.movieTitle.orEmpty()} (${from.movieYear.orEmpty()})",
            description = from.movieStory.orEmpty(),
            ratings = from.movieRatings.orEmpty(),
            posterImage = getPosterImageUrl(HomeCategoryType.HOME_CATEGORY_MOVIE, from.movieID.orEmpty(), from.poster.orEmpty()),
            language = from.movielang.orEmpty(),
            watchLink = from.movieWatchLink.orEmpty(),
            movieSize = from.movieSize.orEmpty(),
            movieRuntime = from.movieRuntime.orEmpty(),
            castAndCrew = from.movieActors.orEmpty(),
            categoriesList =
                buildList {
                    add("Movie")

                    if (true == from.movieCategory?.trim()?.isNotEmpty()) {
                        add(from.movieCategory)
                    }

                    if (true == from.movielang?.trim()?.isNotEmpty()) {
                        add(from.movielang)
                    }

                    if (true == from.movieQuality?.trim()?.isNotEmpty()) {
                        add(from.movieQuality)
                    }

                    if (true == from.movieGenre?.trim()?.isNotEmpty()) {
                        from.movieGenre.split(",").map {
                            add(it)
                        }
                    }
                },
        )

    fun mapGenreToModal(input: List<ResponseModelMovieGenre>?): List<MovieGenreItem> =
        input?.map {
            MovieGenreItem(it.id.orEmpty(), it.name.orEmpty())
        } ?: emptyList()
}
