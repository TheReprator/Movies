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

package dev.reprator.movies.util

import androidx.compose.runtime.Stable

@Stable
sealed interface AppResult<out T> {
    open fun get(): T? = null
}

data class AppSuccess<T>(
    val data: T,
) : AppResult<T> {
    override fun get(): T = data
}

data class AppError(
    val throwable: Throwable? = null,
    val message: String? = null,
    val errorCode: Int? = null,
) : AppResult<Nothing>
