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

import dev.reprator.movies.features.home.domain.models.DisplayableItemError
import dev.reprator.movies.features.home.domain.models.DisplayableItemLoader
import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeSectionModel
import dev.reprator.movies.features.home.domain.models.ResultStatus
import dev.reprator.movies.util.base.mvi.Reducer
import me.tatarka.inject.annotations.Inject

@Inject
class HomeScreenReducer : Reducer<HomeState, HomeAction, HomeEffect> {
    override fun reduce(
        previousState: HomeState,
        action: HomeAction,
    ): Pair<HomeState, HomeEffect?> =
        when (action) {
            is HomeAction.LoadHomeData -> {
                setUpInitialSetup(previousState) to null
            }

            is HomeAction.SectionLoaded -> {
                updateSectionData(previousState, action) to null
            }

            is HomeAction.CarouselPagination -> {
                addCarouselPagination(previousState, action) to null
            }

            is HomeAction.SectionLoadError -> {
                handleErrorState(previousState, action) to null
            }

            is HomeAction.RetrySection -> {
                handleRetryState(previousState, action) to null
            }
            else -> {
                previousState to null
            }
        }

    private fun setUpInitialSetup(previousState: HomeState): HomeState {
        val movieGenre = HomeSectionModel.GenreChipsSection(emptyList())
        val movieModel =
            HomeSectionModel.ItemCarouselSection(
                "Popular this Week",
                emptyList(),
                sectionId = HomeCategoryType.HOME_CATEGORY_MOVIE,
            )
        val tvModel =
            HomeSectionModel.ItemCarouselSection(
                "Recent TV Series",
                emptyList(),
                sectionId = HomeCategoryType.HOME_CATEGORY_TV,
            )

        return previousState.copy(sections = listOf(movieGenre, movieModel, tvModel))
    }

    private fun updateSectionData(
        state: HomeState,
        action: HomeAction.SectionLoaded,
    ): HomeState {
        val updatedSections =
            state.sections.map { section ->
                if (section.sectionId == action.sectionType) {
                    when (section) {
                        is HomeSectionModel.ItemCarouselSection -> {
                            val items = action.items

                            if (section.status == ResultStatus.RESULT_STATUS_LOADER) {
                                if (items.isEmpty()) {
                                    section.copy(status = ResultStatus.RESULT_STATUS_EMPTY)
                                } else {
                                    section.copy(
                                        items = section.items + items,
                                        status = ResultStatus.RESULT_STATUS_RESULT,
                                    )
                                }
                            } else {
                                val filteredNonLoadingList =
                                    section.items.filterNot {
                                        it.itemType == ResultStatus.RESULT_STATUS_LOADER
                                    }
                                section.copy(
                                    items = filteredNonLoadingList + items,
                                )
                            }
                        }

                        is HomeSectionModel.GenreChipsSection -> {
                            val genres = action.genres
                            section.copy(
                                genres = genres,
                                status = ResultStatus.RESULT_STATUS_RESULT,
                            )
                        }
                    }
                } else {
                    section
                }
            }
        return state.copy(sections = updatedSections)
    }

    private fun addCarouselPagination(
        state: HomeState,
        action: HomeAction.CarouselPagination,
    ): HomeState {
        val updatedSections =
            state.sections.map { section ->
                if ((section.sectionId == action.sectionType) && (section is HomeSectionModel.ItemCarouselSection)) {
                    if (section.items.lastOrNull()?.itemType != ResultStatus.RESULT_STATUS_LOADER) {
                        section.copy(items = section.items + DisplayableItemLoader())
                    } else {
                        section
                    }
                } else {
                    section
                }
            }
        return state.copy(sections = updatedSections)
    }

    private fun handleErrorState(
        state: HomeState,
        action: HomeAction.SectionLoadError,
    ): HomeState {
        val updatedSections =
            state.sections.map { section ->
                if (section.sectionId == action.sectionType) {
                    when (section) {
                        is HomeSectionModel.ItemCarouselSection -> {
                            if (section.status == ResultStatus.RESULT_STATUS_LOADER) {
                                section.copy(
                                    status = ResultStatus.RESULT_STATUS_ERROR,
                                )
                            } else {
                                val filteredNonLoadingList =
                                    section.items.filterNot {
                                        it.itemType == ResultStatus.RESULT_STATUS_LOADER
                                    }
                                section.copy(items = filteredNonLoadingList + DisplayableItemError())
                            }
                        }

                        is HomeSectionModel.GenreChipsSection ->
                            section.copy(
                                status = ResultStatus.RESULT_STATUS_ERROR,
                            )
                    }
                } else {
                    section
                }
            }
        return state.copy(sections = updatedSections)
    }

    private fun handleRetryState(
        state: HomeState,
        action: HomeAction.RetrySection,
    ): HomeState {
        val updatedSections =
            state.sections.map { section ->
                if (section.sectionId == action.sectionType) {
                    when (section) {
                        is HomeSectionModel.ItemCarouselSection -> {
                            if (section.status == ResultStatus.RESULT_STATUS_ERROR) {
                                section.copy(status = ResultStatus.RESULT_STATUS_LOADER)
                            } else {
                                val filteredNonLoadingList =
                                    section.items.filterNot {
                                        it.itemType == ResultStatus.RESULT_STATUS_ERROR
                                    }
                                section.copy(items = filteredNonLoadingList + DisplayableItemLoader())
                            }
                        }

                        is HomeSectionModel.GenreChipsSection -> section.copy(status = ResultStatus.RESULT_STATUS_LOADER)
                    }
                } else {
                    section
                }
            }
        return state.copy(sections = updatedSections)
    }
}
