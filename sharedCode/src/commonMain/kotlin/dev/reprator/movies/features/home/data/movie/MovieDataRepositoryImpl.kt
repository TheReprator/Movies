package dev.reprator.movies.features.home.data.movie

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieItem
import dev.reprator.movies.features.home.domain.repository.MovieRepository
import dev.reprator.movies.util.AppResult
import me.tatarka.inject.annotations.Inject

@Inject
class MovieDataRepositoryImpl (private val dataRepository: MovieDataRepository):
    MovieRepository {

    override suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieItem>> {
       return dataRepository.getMovieList(pageCount)
    }

    override suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>> {
        return dataRepository.getMovieGenre()
    }
}