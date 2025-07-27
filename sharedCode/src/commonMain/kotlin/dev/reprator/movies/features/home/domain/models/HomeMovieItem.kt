package dev.reprator.movies.features.home.domain.models

data class HomeMovieItem(
    override val id: String,
    override val typeId: String,
    override val name: String,
    override val description: String,
    override val ratings: String,
    override val posterImage: String,
    override val language: String,
    override val categoriesList: List<String>,
    override val categoryType: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_MOVIE,
    val watchLink: String,
    val movieSize: String,
    val movieRuntime: String,
    val castAndCrew: String,
): CategoryItem


data class HomeEpisodeOverView(
    override val id: String,
    override val typeId: String,
    override val name: String,
    override val description: String,
    override val ratings: String,
    override val posterImage: String,
    override val language: String,
    override val categoriesList: List<String>,
    override val categoryType: HomeCategoryType = HomeCategoryType.HOME_CATEGORY_TV,
): CategoryItem


data class MovieGenreItem(val id: String, val name: String)