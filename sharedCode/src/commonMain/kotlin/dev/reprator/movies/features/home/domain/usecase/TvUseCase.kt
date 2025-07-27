package dev.reprator.movies.features.home.domain.usecase

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.features.home.domain.repository.TvRepository
import dev.reprator.movies.util.AppResult
import me.tatarka.inject.annotations.Inject

@Inject
class TvUseCaseImpl(private val repository: TvRepository): TvUseCase {
    override suspend fun getTvList(pageCount: Int): AppResult<List<HomeEpisodeOverView>> {
        return repository.getTvList(pageCount)
    }
}

@Inject
interface TvUseCase {
    suspend fun getTvList(pageCount: Int): AppResult<List<HomeEpisodeOverView>>
}