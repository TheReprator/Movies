package dev.reprator.movies.features.home.domain.repository

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.util.AppResult

interface TvRepository {
    suspend fun getTvList(pageCount: Int = 1): AppResult<List<HomeEpisodeOverView>>
}