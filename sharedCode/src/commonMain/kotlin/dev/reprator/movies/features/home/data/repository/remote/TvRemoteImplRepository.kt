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

import dev.reprator.movies.features.home.data.repository.remote.MovieRemoteImplRepository.Companion.QUERY_MAP
import dev.reprator.movies.features.home.data.repository.remote.MovieRemoteImplRepository.Companion.QUERY_PAGE
import dev.reprator.movies.features.home.data.repository.remote.model.tv.ResponseModelTv
import dev.reprator.movies.features.home.data.serials.TvDataRepository
import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.util.AppError
import dev.reprator.movies.util.AppResult
import dev.reprator.movies.util.AppSuccess
import dev.reprator.movies.util.api.Mapper
import dev.reprator.movies.util.api.hitApiWithClient
import dev.reprator.movies.util.api.toListMapper
import io.ktor.client.HttpClient
import io.ktor.util.appendAll
import me.tatarka.inject.annotations.Inject

@Inject
class TvRemoteImplRepository(
    private val httpClient: Lazy<HttpClient>,
    private val mapper: Mapper<ResponseModelTv, HomeEpisodeOverView>,
) : TvDataRepository {
    override suspend fun getTvList(pageCount: Int): AppResult<List<HomeEpisodeOverView>> {
        val response =
            httpClient.value.hitApiWithClient<List<ResponseModelTv>>(
                endPoint = TV_API,
                changeBlock = {
                    appendAll(QUERY_MAP)
                    append(QUERY_PAGE, pageCount.toString())
                },
            ) {
            }

        return when (response) {
            is AppSuccess -> {
                AppSuccess(mapper.toListMapper()(response.data))
            }

            is AppError -> {
                response
            }
        }
    }

    companion object {
        private const val TV_API = "/api/v1/tvshows.php"
    }
}
