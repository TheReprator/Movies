package dev.reprator.movies.features.home.data.repository.remote


import dev.reprator.movies.features.home.data.movie.MovieDataRepository
import dev.reprator.movies.features.home.data.repository.remote.model.movie.MovieMapper
import dev.reprator.movies.features.home.data.repository.remote.model.movie.ResponseModelMovie
import dev.reprator.movies.features.home.data.repository.remote.model.movie.ResponseModelMovieGenre
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieItem
import dev.reprator.movies.util.AppError
import dev.reprator.movies.util.AppResult
import dev.reprator.movies.util.AppSuccess
import dev.reprator.movies.util.api.hitApiWithClient
import dev.reprator.movies.util.api.toListMapper
import io.ktor.client.HttpClient
import io.ktor.util.appendAll
import me.tatarka.inject.annotations.Inject

@Inject
class MovieRemoteImplRepository(
    private val httpClient: Lazy<HttpClient>, private val mapper: MovieMapper
) : MovieDataRepository {

    override suspend fun getMovieList(pageCount: Int): AppResult<List<HomeMovieItem>> {
        val response = httpClient.value.hitApiWithClient<List<ResponseModelMovie>>(
            endPoint = MOVIE_API,
            changeBlock = {
                appendAll(QUERY_AP)
                append(QUERY_PAGE, pageCount.toString())
            })

        return when (response) {
            is AppSuccess -> {
                AppSuccess(mapper.toListMapper()(response.data))
            }

            is AppError -> {
                response
            }
        }
    }

    override suspend fun getMovieGenre(): AppResult<List<MovieGenreItem>> {
        val response = httpClient.value.hitApiWithClient<List<ResponseModelMovieGenre>>(
            endPoint = MOVIE_GENRE
        )

        return when (response) {
            is AppSuccess -> {
                AppSuccess(mapper.mapGenreToModal(response.data))
            }

            is AppError -> {
                response
            }
        }
    }

    companion object {
        private const val MOVIE_API = "/api/v1/movies.php"
        private const val MOVIE_GENRE = "/api/v1/moviegenre.php"
        const val QUERY_PAGE = "page"
        val QUERY_AP = mapOf("sort_by" to "uploadTime DESC", "limit" to "10")
    }
}