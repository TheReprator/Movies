package dev.reprator.movies.features.home.domain.models

interface CategoryDisplayableItem: DisplayableItem {
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


interface DisplayableItem {
    val itemType: ResultStatus
    val id: String
}


class DisplayableItemLoader: DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_LOADER
    override val id: String = "DisplayableItemLoader"
}

class DisplayableItemEmpty: DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_EMPTY
    override val id: String = "DisplayableItemEmpty"
}

data class DisplayableItemError(val message: String = "Failed to load item"): DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_ERROR
    override val id: String = "DisplayableItemError"
}
