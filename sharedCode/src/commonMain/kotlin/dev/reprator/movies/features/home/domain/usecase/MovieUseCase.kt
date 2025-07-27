package dev.reprator.movies.features.home.domain.usecase

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
import dev.reprator.movies.features.home.domain.repository.MovieRepository
import dev.reprator.movies.util.AppResult
import me.tatarka.inject.annotations.Inject

@Inject
class MovieUseCaseImpl(private val repository: MovieRepository): MovieUseCase {

    override suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieDisplayableItem>>{
        return repository.getMovieList(pageCount)
    }

    override suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>> {
        return repository.getMovieGenre()
    }
}

interface MovieUseCase {

    suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieDisplayableItem>>

    suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>>
}