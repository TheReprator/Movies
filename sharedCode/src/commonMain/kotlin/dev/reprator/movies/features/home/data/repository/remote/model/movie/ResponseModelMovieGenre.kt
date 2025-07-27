package dev.reprator.movies.features.home.data.repository.remote.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModelMovieGenre(
    val id: String?,
    val name: String?,
)