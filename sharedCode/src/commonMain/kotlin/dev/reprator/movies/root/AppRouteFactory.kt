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

package dev.reprator.movies.root

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface AppRouteFactory {
    fun NavGraphBuilder.create(
        navController: NavController,
        modifier: Modifier,
    )
}

fun NavGraphBuilder.create(
    factories: Set<AppRouteFactory>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    factories.forEach { factory ->
        with(factory) {
            this@create.create(
                navController = navController,
                modifier = modifier,
            )
        }
    }
}
