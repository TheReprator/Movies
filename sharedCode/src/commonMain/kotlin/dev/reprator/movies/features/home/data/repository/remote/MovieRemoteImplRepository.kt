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

package dev.reprator.movies.features.home.data.repository.remote

import dev.reprator.movies.features.home.data.movie.MovieDataRepository
import dev.reprator.movies.features.home.data.repository.remote.model.movie.MovieMapper
import dev.reprator.movies.features.home.data.repository.remote.model.movie.ResponseModelMovie
import dev.reprator.movies.features.home.data.repository.remote.model.movie.ResponseModelMovieGenre
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.util.AppError
import dev.reprator.movies.util.AppResult
import dev.reprator.movies.util.AppSuccess
import dev.reprator.movies.util.api.hitApiWithClient
import dev.reprator.movies.util.api.toListMapper
import io.ktor.client.HttpClient
import io.ktor.util.appendAll
import me.tatarka.inject.annotations.Inject

@Inject
class MovieRemoteImplRepository(
    private val httpClient: Lazy<HttpClient>,
    private val mapper: MovieMapper,
) : MovieDataRepository {
    override suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieDisplayableItem>> {
        val response =
            httpClient.value.hitApiWithClient<List<ResponseModelMovie>>(
                endPoint = MOVIE_API,
                changeBlock = {
                    appendAll(QUERY_MAP)
                    append(QUERY_PAGE, pageCount.toString())
                },
            )

        return when (response) {
            is AppSuccess -> {
                AppSuccess(mapper.toListMapper()(response.data))
            }

            is AppError -> {
                response
            }
        }
    }

    override suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>> {
        val response =
            httpClient.value.hitApiWithClient<List<ResponseModelMovieGenre>>(
                endPoint = MOVIE_GENRE,
            )

        return when (response) {
            is AppSuccess -> {
                AppSuccess(mapper.mapGenreToModal(response.data))
            }

            is AppError -> {
                response
            }
        }
    }

    companion object {
        private const val MOVIE_API = "/api/v1/movies.php"
        private const val MOVIE_GENRE = "/api/v1/moviegenre.php"
        const val QUERY_PAGE = "page"
        val QUERY_MAP = mapOf("sort_by" to "uploadTime DESC", "limit" to "10")
    }
}
