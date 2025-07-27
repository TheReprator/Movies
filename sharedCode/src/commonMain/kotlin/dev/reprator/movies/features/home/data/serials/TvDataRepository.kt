package dev.reprator.movies.features.home.data.serials

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.util.AppResult

interface TvDataRepository {
    suspend fun getTvList(pageCount: Int): AppResult<List<HomeEpisodeOverView>>
}
