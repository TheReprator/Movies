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

package dev.reprator.movies.features.home.data.repository.remote.model.tv

import dev.reprator.movies.BuildConfig
import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.util.api.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class TvMapper : Mapper<ResponseModelTv, HomeEpisodeOverView> {
    override suspend fun map(from: ResponseModelTv): HomeEpisodeOverView =
        HomeEpisodeOverView(
            id = from.id.orEmpty(),
            typeId = from.tvId.orEmpty(),
            name = "${from.tvTitle.orEmpty()} (${from.tvYear.orEmpty()})",
            description = from.tvStory.orEmpty(),
            ratings = from.tvRatings.orEmpty(),
            posterImage = getPosterImageUrl(HomeCategoryType.HOME_CATEGORY_TV, from.tvId.orEmpty(), from.poster.orEmpty()),
            language = from.tvlang.orEmpty(),
            categoriesList =
                buildList {
                    if (true == from.tvCategory?.trim()?.isNotEmpty()) {
                        add(from.tvCategory)
                    }

                    if (true == from.tvlang?.trim()?.isNotEmpty()) {
                        add(from.tvlang)
                    }

                    if (true == from.tvGenre?.trim()?.isNotEmpty()) {
                        from.tvGenre.split(",").map {
                            add(it)
                        }
                    }
                },
        )

    companion object {
        fun getPosterImageUrl(
            type: HomeCategoryType,
            id: String,
            posterImage: String?,
        ): String {
            if (true == posterImage?.trim()?.isEmpty()) {
                return ""
            }
            val baseUrl = BuildConfig.BASE_URL

            val imageUrl =
                when (type) {
                    HomeCategoryType.HOME_CATEGORY_MOVIE -> "http://$baseUrl/Admin/main/images/$id/poster/$posterImage"
                    HomeCategoryType.HOME_CATEGORY_TV -> "http://$baseUrl/Admin/main/TVseries/$id/poster/$posterImage"
                    else -> ""
                }

            return imageUrl
        }
    }
}
