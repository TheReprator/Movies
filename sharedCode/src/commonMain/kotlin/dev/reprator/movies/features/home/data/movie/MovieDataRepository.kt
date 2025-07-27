package dev.reprator.movies.features.home.data.movie

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieItem
import dev.reprator.movies.util.AppResult

interface MovieDataRepository {
    suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieItem>>
    suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>>
}
