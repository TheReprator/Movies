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

package dev.reprator.movies.features.home.presentation.ui

import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.reprator.movies.features.home.presentation.HomeViewModel
import dev.reprator.movies.root.AppRouteFactory
import dev.reprator.movies.root.navigation.Route
import me.tatarka.inject.annotations.Inject

@Inject
class HomeRouteFactory(
    private val viewModelFactory: (SavedStateHandle) -> HomeViewModel,
) : AppRouteFactory {
    override fun NavGraphBuilder.create(
        navController: NavController,
        modifier: Modifier,
    ) {
        composable<Route.Home> { _ ->
            val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
            HomeScreen(viewModel, navController, modifier)
        }
    }
}
