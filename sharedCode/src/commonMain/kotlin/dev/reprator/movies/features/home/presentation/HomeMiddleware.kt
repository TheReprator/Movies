package dev.reprator.movies.features.home.presentation

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.features.home.domain.models.HomeMovieItem
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.usecase.MovieUseCase
import dev.reprator.movies.features.home.domain.usecase.TvUseCase
import dev.reprator.movies.util.AppError
import dev.reprator.movies.util.AppSuccess
import dev.reprator.movies.util.base.mvi.Middleware
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.CoroutineContext

@Inject
class HomeMiddleware(
    private val moveUseCase: MovieUseCase,
    private val tvUseCase: TvUseCase,
    private val dispatchers: AppCoroutineDispatchers
) : Middleware<HomeState, HomeAction, HomeEffect>, CoroutineScope {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e(throwable) { "Coroutine exception" }
    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + coroutineExceptionHandler

    private val mviDispatcher = CompletableDeferred<(HomeAction) -> Unit>()

    private var moviePaginationCount = 1
    private var tvPaginationCount = 1

    override fun close() {
        coroutineContext.cancelChildren()
    }

    override fun attach(dispatcher: (HomeAction) -> Unit) {
        mviDispatcher.complete(dispatcher)
    }

    override fun onAction(
        action: HomeAction,
        state: HomeState
    ) {
        when (action) {

            is HomeAction.UpdateHomeList -> {
                handleApi()
            }

            is HomeAction.RetryTv -> {
                fetchTvEpisodes()
            }

            is HomeAction.RetryMovie -> {
                fetchMovies()
            }

            is HomeAction.RetryMovieGenre -> {
                fetchMovieGenre()
            }

            else -> {

            }
        }
    }

    private fun handleApi() {
       /* fetchMovies()
        fetchMovieGenre()
        fetchTvEpisodes()*/
    }

    private fun fetchTvEpisodes() {
        launch(dispatchers.io) {
            val result = tvUseCase.getTvList(tvPaginationCount)
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<List<HomeEpisodeOverView>> -> {
                        tvPaginationCount++
                        mviDispatcher.await()(HomeAction.UpdateHomeTvList(result.data))
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.UpdateHomeTvError(
                                result.message ?: result.throwable?.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }

    private fun fetchMovies() {
        launch(dispatchers.io) {
            val result = moveUseCase.getMovieList(moviePaginationCount)
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<List<HomeMovieItem>> -> {
                        moviePaginationCount++
                        mviDispatcher.await()(HomeAction.UpdateHomeMovieList(result.data))
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.UpdateHomeMovieError(
                                result.message ?: result.throwable?.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }

    private fun fetchMovieGenre() {
        launch(dispatchers.io) {
            val result = moveUseCase.getMovieGenre()
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<List<MovieGenreItem>> -> {
                        mviDispatcher.await()(HomeAction.UpdateHomeGenreList(result.data))
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.UpdateHomeMovieError(
                                result.message ?: result.throwable?.message ?: ""
                            )
                        )
                    }
                }
            }
        }
    }
}