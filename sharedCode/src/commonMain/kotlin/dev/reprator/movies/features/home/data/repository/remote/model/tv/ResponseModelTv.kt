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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModelTv(
    val id: String?,
    @SerialName("TVID")
    val tvId: String?,
    @SerialName("TVtitle")
    val tvTitle: String?,
    @SerialName("TVrelease")
    val tvYear: String?,
    @SerialName("TVcategory")
    val tvCategory: String?,
    @SerialName("TVtrailer")
    val tvTrailerVideoIdYoutube: String?,
    @SerialName("TVRatings")
    val tvRatings: String?,
    @SerialName("TVgenre")
    val tvGenre: String?,
    @SerialName("TVlang")
    val tvlang: String?,
    @SerialName("TVKeywords")
    val tvKeywords: String?,
    @SerialName("TVStory")
    val tvStory: String?,
    @SerialName("TVSubtitle")
    val tvSubtitle: String?,
    @SerialName("TVActors")
    val tvActors: String?,
    @SerialName("TVposter")
    val poster: String?,
)
