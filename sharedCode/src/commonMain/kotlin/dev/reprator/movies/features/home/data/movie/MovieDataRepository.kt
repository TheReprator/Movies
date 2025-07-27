package dev.reprator.movies.features.home.data.movie

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
import dev.reprator.movies.util.AppResult

interface MovieDataRepository {
    suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieDisplayableItem>>
    suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>>
}
