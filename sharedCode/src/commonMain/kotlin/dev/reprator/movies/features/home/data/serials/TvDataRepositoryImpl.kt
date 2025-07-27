package dev.reprator.movies.features.home.data.serials

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.features.home.domain.repository.TvRepository
import dev.reprator.movies.util.AppResult
import me.tatarka.inject.annotations.Inject

@Inject
class TvDataRepositoryImpl(private val dataRepository: TvDataRepository) : TvRepository {

    override suspend fun getTvList(pageCount: Int): AppResult<List<HomeEpisodeOverView>> =
        dataRepository.getTvList(pageCount)
}