package dev.reprator.movies.features.home.presentation

import dev.reprator.movies.features.home.domain.models.CategoryItem
import dev.reprator.movies.features.home.domain.models.HomeModel
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.util.base.mvi.SideEffect
import dev.reprator.movies.util.base.mvi.UiAction
import dev.reprator.movies.util.base.mvi.UiState


sealed interface HomeAction : UiAction {
    object UpdateHomeList : HomeAction

    data class UpdateHomeTvList(val itemList: List<CategoryItem>) : HomeAction
    data class UpdateHomeTvError(val error: String) : HomeAction
    object RetryTv : HomeAction

    data class UpdateHomeMovieList(val itemList: List<CategoryItem>) : HomeAction
    data class UpdateHomeMovieError(val error: String) : HomeAction
    object RetryMovie : HomeAction

    data class UpdateHomeGenreList(val itemList: List<MovieGenreItem>) : HomeAction
    object RetryMovieGenre : HomeAction
}

sealed interface HomeEffect : SideEffect {
    data class ShowError(val message: String) : HomeEffect
}

data class HomeState(
    val itemList: List<HomeModel>
) : UiState {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                itemList = emptyList()
            )
        }
    }
}