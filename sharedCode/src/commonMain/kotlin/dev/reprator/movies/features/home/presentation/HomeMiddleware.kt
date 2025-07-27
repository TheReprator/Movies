/*
 * Copyright 2025 @TheReprator
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.reprator.movies.features.home.presentation

import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
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
    private val dispatchers: AppCoroutineDispatchers,
) : Middleware<HomeState, HomeAction, HomeEffect>,
    CoroutineScope {

    private val sectionCurrentPage = mutableMapOf<HomeCategoryType, Int>()
    private val sectionHasMore = mutableMapOf<HomeCategoryType, Boolean>()

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Napier.e(throwable) { "Coroutine exception" }
        }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + coroutineExceptionHandler

    private val mviDispatcher = CompletableDeferred<(HomeAction) -> Unit>()

    override fun close() {
        coroutineContext.cancelChildren()
    }

    override fun attach(dispatcher: (HomeAction) -> Unit) {
        mviDispatcher.complete(dispatcher)
    }

    override fun onAction(
        action: HomeAction,
        state: HomeState,
    ) {
        when (action) {
            is HomeAction.LoadHomeData -> {
                sectionCurrentPage.clear()
                sectionHasMore.clear()
                sectionCurrentPage.put(HomeCategoryType.HOME_CATEGORY_MOVIE, 1)
                sectionCurrentPage.put(HomeCategoryType.HOME_CATEGORY_TV, 1)
                handleApi()
            }

            is HomeAction.RetrySection -> {
                fetchSection(action.sectionType)
            }

            is HomeAction.CarouselPagination -> {
                fetchSection(action.sectionType)
            }

            else -> {
            }
        }
    }

    private fun handleApi() {
        fetchMovies()
        fetchMovieGenre()
        fetchTvEpisodes()
    }

    private fun fetchTvEpisodes() {
        launch(dispatchers.io) {
            val sectionValue = sectionHasMore.getOrElse(HomeCategoryType.HOME_CATEGORY_TV) { true }
            if (!sectionValue) {
                mviDispatcher.await()(
                    HomeAction.SectionLoaded(
                        HomeCategoryType.HOME_CATEGORY_TV,
                    ),
                )
                return@launch
            }

            val currentTvPage = sectionCurrentPage[HomeCategoryType.HOME_CATEGORY_TV]!!
            val result = tvUseCase.getTvList(currentTvPage)
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<List<HomeEpisodeOverView>> -> {
                        sectionCurrentPage[HomeCategoryType.HOME_CATEGORY_TV] = currentTvPage + 1
                        if (result.data.isEmpty()) {
                            sectionHasMore[HomeCategoryType.HOME_CATEGORY_TV] = false
                        }

                        mviDispatcher.await() (
                            HomeAction.SectionLoaded(
                                sectionType = HomeCategoryType.HOME_CATEGORY_TV,
                                items = result.data,
                            ),
                        )
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.SectionLoadError(
                                HomeCategoryType.HOME_CATEGORY_TV,
                                result.message ?: result.throwable?.message ?: "",
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun fetchMovies() {
        launch(dispatchers.io) {
            val sectionValue = sectionHasMore.getOrElse(HomeCategoryType.HOME_CATEGORY_MOVIE) { true }
            if (!sectionValue) {
                mviDispatcher.await()(
                    HomeAction.SectionLoaded(
                        HomeCategoryType.HOME_CATEGORY_MOVIE,
                    ),
                )
                return@launch
            }

            val currentMoviePage = sectionCurrentPage[HomeCategoryType.HOME_CATEGORY_MOVIE]!!
            val result = moveUseCase.getMovieList(currentMoviePage)
            withContext(dispatchers.main) {
                when (result) {
                    is AppSuccess<List<HomeMovieDisplayableItem>> -> {
                        if (result.data.isEmpty()) {
                            sectionHasMore[HomeCategoryType.HOME_CATEGORY_MOVIE] = false
                        }
                        sectionCurrentPage[HomeCategoryType.HOME_CATEGORY_MOVIE] = currentMoviePage + 1
                        mviDispatcher.await()(
                            HomeAction.SectionLoaded(
                                sectionType = HomeCategoryType.HOME_CATEGORY_MOVIE,
                                items = result.data,
                            ),
                        )
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.SectionLoadError(
                                HomeCategoryType.HOME_CATEGORY_MOVIE,
                                result.message ?: result.throwable?.message ?: "",
                            ),
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
                        mviDispatcher.await()(
                            HomeAction.SectionLoaded(
                                sectionType = HomeCategoryType.HOME_CATEGORY_GENRE,
                                genres = result.data,
                            ),
                        )
                    }

                    is AppError -> {
                        mviDispatcher.await()(
                            HomeAction.SectionLoadError(
                                HomeCategoryType.HOME_CATEGORY_GENRE,
                                result.message ?: result.throwable?.message ?: "",
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun fetchSection(sectionType: HomeCategoryType) {
        when (sectionType) {
            HomeCategoryType.HOME_CATEGORY_MOVIE -> {
                fetchMovies()
            }

            HomeCategoryType.HOME_CATEGORY_TV -> {
                fetchTvEpisodes()
            }

            HomeCategoryType.HOME_CATEGORY_GENRE -> {
                fetchMovieGenre()
            }
        }
    }
}
