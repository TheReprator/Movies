package dev.reprator.movies.features.settings.presentation.ui

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
class SettingsRouteFactory(
    private val viewModelFactory: (SavedStateHandle) -> HomeViewModel
) : AppRouteFactory {

    override fun NavGraphBuilder.create(
        navController: NavController,
        modifier: Modifier
    ) {
        composable<Route.Settings> { _ ->
            val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
            SettingsScreen(viewModel, navController, modifier)
        }
    }
}