package dev.reprator.movies.features.home.presentation

import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeSectionModel
import dev.reprator.movies.features.home.domain.models.DisplayableItem
import dev.reprator.movies.features.home.domain.models.HomeSectionLayoutType
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.util.base.mvi.SideEffect
import dev.reprator.movies.util.base.mvi.UiAction
import dev.reprator.movies.util.base.mvi.UiState


sealed interface HomeAction : UiAction {
    object LoadHomeData : HomeAction
    data class SectionLoaded(
        val sectionType: HomeCategoryType,
        val items: List<DisplayableItem> = emptyList(),
        val genres: List<MovieGenreItem> = emptyList()
    ) : HomeAction
    data class SectionLoadError(val sectionType: HomeCategoryType, val error: String) : HomeAction
    data class RetrySection(val sectionType: HomeCategoryType) : HomeAction
    data class CarouselPagination(val sectionType: HomeCategoryType) : HomeAction
}


sealed interface HomeEffect : SideEffect {
    data class ShowError(val message: String) : HomeEffect
}

data class HomeState(
    val sections: List<HomeSectionModel>
) : UiState {
    companion object {
        fun initial(): HomeState {
            return HomeState(
                sections = emptyList()
            )
        }
    }
}