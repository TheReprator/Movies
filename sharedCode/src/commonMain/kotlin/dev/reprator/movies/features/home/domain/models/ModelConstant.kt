package dev.reprator.movies.features.home.domain.models

enum class ResultStatus {
    RESULT_STATUS_LOADER,
    RESULT_STATUS_ERROR,
    RESULT_STATUS_EMPTY,
    RESULT_STATUS_RESULT
}

enum class HomeCategoryType {
    HOME_CATEGORY_MOVIE,
    HOME_CATEGORY_TV,
    HOME_CATEGORY_GENRE
}

sealed interface HomeModel {
    val id: HomeCategoryType
    val resultStatus: ResultStatus

    data class ModelGenre(val genreList: List<MovieGenreItem>,
                     override val id: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_GENRE,
                     override val resultStatus: ResultStatus = ResultStatus.RESULT_STATUS_LOADER
    ): HomeModel

    data class ModelMovie(val categoryName: String, val itemList: List<ItemType>,
                     override val id: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_MOVIE,
                     override val resultStatus: ResultStatus = ResultStatus.RESULT_STATUS_LOADER): HomeModel

    data class ModelTvSeries(val categoryName: String, val itemList: List<ItemType>,
                        override val id: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_TV,
                        override val resultStatus: ResultStatus = ResultStatus.RESULT_STATUS_LOADER): HomeModel
}
