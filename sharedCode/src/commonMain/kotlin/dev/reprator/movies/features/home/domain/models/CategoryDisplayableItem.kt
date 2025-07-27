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

package dev.reprator.movies.features.home.domain.models

interface CategoryDisplayableItem : DisplayableItem {
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

class DisplayableItemLoader : DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_LOADER
    override val id: String = "DisplayableItemLoader"
}

class DisplayableItemEmpty : DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_EMPTY
    override val id: String = "DisplayableItemEmpty"
}

data class DisplayableItemError(
    val message: String = "Failed to load item",
) : DisplayableItem {
    override val itemType: ResultStatus = ResultStatus.RESULT_STATUS_ERROR
    override val id: String = "DisplayableItemError"
}
