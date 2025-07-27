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

enum class HomeSectionLayoutType {
    POSTER_CAROUSEL,
    GENRE_CHIPS
}

sealed interface HomeSectionModel {
    val sectionId: HomeCategoryType
    val status: ResultStatus
    val layoutType: HomeSectionLayoutType

    data class ItemCarouselSection(
        val categoryName: String,
        val items: List<DisplayableItem>,
        override val sectionId: HomeCategoryType,
        override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
        override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.POSTER_CAROUSEL
    ) : HomeSectionModel

    data class GenreChipsSection(
        val genres: List<MovieGenreItem>,
        override val sectionId: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_GENRE,
        override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
        override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.GENRE_CHIPS
    ) : HomeSectionModel

   /* data class SectionModelGenre(val genreList: List<MovieGenreItem>,
                                 override val sectionId: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_GENRE,
                                 override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
                                 override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.GENRE_CHIPS
    ): HomeSectionModel

    data class SectionModelMovie(val categoryName: String, val itemList: List<ItemType>,
                                 override val sectionId: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_MOVIE,
                                 override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
                                 override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.POSTER_CAROUSEL
        ): HomeSectionModel

    data class SectionModelTvSeries(val categoryName: String, val itemList: List<ItemType>,
                                    override val sectionId: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_TV,
                                    override val status: ResultStatus = ResultStatus.RESULT_STATUS_LOADER,
                                    override val layoutType: HomeSectionLayoutType = HomeSectionLayoutType.POSTER_CAROUSEL): HomeSectionModel*/
}
