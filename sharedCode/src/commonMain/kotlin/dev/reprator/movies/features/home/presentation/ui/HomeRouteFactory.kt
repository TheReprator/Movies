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
    private val viewModelFactory: (SavedStateHandle) -> HomeViewModel
) : AppRouteFactory {

    override fun NavGraphBuilder.create(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<Route.Home> { _ ->
            val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
            HomeScreen(viewModel, navController, modifier)
        }
    }
}