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