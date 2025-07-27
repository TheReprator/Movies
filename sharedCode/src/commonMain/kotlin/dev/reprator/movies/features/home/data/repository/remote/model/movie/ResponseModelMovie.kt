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