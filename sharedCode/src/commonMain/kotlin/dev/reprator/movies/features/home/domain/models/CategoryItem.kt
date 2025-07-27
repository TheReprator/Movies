package dev.reprator.movies.features.home.domain.models

interface CategoryItem: ItemType {
    val id: String
    val typeId: String
    val name: String
    val description: String
    val ratings: String
    val posterImage: String
    val language: String
    val categoriesList: List<String>
    val categoryType: HomeCategoryType
    override val itemType: ResultStatus
        get() = ResultStatus.RESULT_STATUS_RESULT
}


interface ItemType {
    val itemType: ResultStatus
}


class ItemTypeLoader: ItemType {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_LOADER
}

class ItemTypeEmpty: ItemType {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_EMPTY
}

class ItemTypeError: ItemType {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_ERROR
}
