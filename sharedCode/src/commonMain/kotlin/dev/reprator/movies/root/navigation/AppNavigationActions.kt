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

package dev.reprator.movies.root.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import movies.sharedcode.generated.resources.Res
import movies.sharedcode.generated.resources.nav_home
import movies.sharedcode.generated.resources.nav_library
import movies.sharedcode.generated.resources.nav_movies
import movies.sharedcode.generated.resources.nav_setting
import org.jetbrains.compose.resources.StringResource

/**
 * Different position of navigation content inside Navigation Rail, Navigation Drawer depending on device size and state.
 */
enum class AppNavigationContentPosition {
    TOP,
    CENTER,
}

enum class LayoutType {
    HEADER,
    CONTENT,
}

enum class AppContentType {
    SINGLE_PANE,
    DUAL_PANE,
}

enum class AppNavigationType {
    BOTTOM_NAVIGATION,
    NAVIGATION_RAIL,
    PERMANENT_NAVIGATION_DRAWER,
    ;

    companion object {
        fun NavigationSuiteType.toAppNavType() =
            when (this) {
                NavigationSuiteType.NavigationBar -> BOTTOM_NAVIGATION
                NavigationSuiteType.NavigationRail -> NAVIGATION_RAIL
                NavigationSuiteType.NavigationDrawer -> PERMANENT_NAVIGATION_DRAWER
                else -> BOTTOM_NAVIGATION
            }
    }
}

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Movies : Route

    @Serializable
    data object Library : Route

    @Serializable
    data object Settings : Route
}

@Immutable
data class AppTopLevelDestination(
    val screen: Route,
    val label: StringResource,
    val contentDescription: StringResource,
    val iconImageVector: ImageVector,
    val selectedImageVector: ImageVector,
)

val TOP_LEVEL_DESTINATIONS =
    listOf(
        AppTopLevelDestination(
            screen = Route.Home,
            label = Res.string.nav_home,
            contentDescription = Res.string.nav_home,
            iconImageVector = Icons.Outlined.Home,
            selectedImageVector = Icons.Default.Home,
        ),
        AppTopLevelDestination(
            screen = Route.Movies,
            label = Res.string.nav_movies,
            contentDescription = Res.string.nav_movies,
            iconImageVector = Icons.Outlined.Movie,
            selectedImageVector = Icons.Default.Movie,
        ),
        AppTopLevelDestination(
            screen = Route.Library,
            label = Res.string.nav_library,
            contentDescription = Res.string.nav_library,
            iconImageVector = Icons.Outlined.LibraryAdd,
            selectedImageVector = Icons.Default.LibraryAdd,
        ),
        AppTopLevelDestination(
            screen = Route.Settings,
            label = Res.string.nav_setting,
            contentDescription = Res.string.nav_setting,
            iconImageVector = Icons.Outlined.Settings,
            selectedImageVector = Icons.Default.Settings,
        ),
    )

class AppNavigationActions(
    private val navController: NavHostController,
) {
    fun navigateTo(destination: AppTopLevelDestination) {
        navController.navigate(destination.screen) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

fun NavDestination?.hasRoute(destination: AppTopLevelDestination): Boolean = this?.hasRoute(destination.screen::class) ?: false
