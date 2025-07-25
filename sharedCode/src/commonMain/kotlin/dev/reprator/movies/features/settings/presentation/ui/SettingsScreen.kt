package dev.reprator.movies.features.settings.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.reprator.movies.features.home.presentation.HomeViewModel
import me.tatarka.inject.annotations.Inject


@Inject
@Composable
fun SettingsScreen(
    userListViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Text("Setting Screen")
}