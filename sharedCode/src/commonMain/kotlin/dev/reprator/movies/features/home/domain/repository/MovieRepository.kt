package dev.reprator.movies.features.home.domain.repository

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieItem
import dev.reprator.movies.util.AppResult

interface MovieRepository {
    suspend fun getMovieList(pageCount: Int = 1): AppResult<List<HomeMovieItem>>
    suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>>
}