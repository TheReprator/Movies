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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModelMovie(
    val id: String?,
    @SerialName("MovieID")
    val movieID: String?,
    @SerialName("MovieTitle")
    val movieTitle: String?,
    @SerialName("MovieYear")
    val movieYear: String?,
    @SerialName("MovieQuality")
    val movieQuality: String?,
    @SerialName("MovieCategory")
    val movieCategory: String?,
    @SerialName("MovieTrailer")
    val movieTrailerVideoIdYoutube: String?,
    @SerialName("MovieRatings")
    val movieRatings: String?,
    @SerialName("MovieGenre")
    val movieGenre: String?,
    @SerialName("MovieDate")
    val movieDate: String?,
    @SerialName("Movielang")
    val movielang: String?,
    @SerialName("MovieRuntime")
    val movieRuntime: String?,
    @SerialName("MovieKeywords")
    val movieKeywords: String?,
    @SerialName("MovieStory")
    val movieStory: String?,
    @SerialName("MovieWatchLink")
    val movieWatchLink: String?,
    @SerialName("MovieSubtitle")
    val movieSubtitle: String?,
    @SerialName("MovieActors")
    val movieActors: String?,
    @SerialName("MovieSize")
    val movieSize: String?,
    val poster: String?,
)
