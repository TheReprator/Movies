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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.util.DebugLogger
import dev.reprator.movies.root.navigation.AppNavigationActions
import dev.reprator.movies.root.navigation.MoviesNavigationWrapper
import dev.reprator.movies.root.navigation.Route
import dev.reprator.movies.util.wrapper.ApplicationInfo
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Inject
import okio.Path.Companion.toPath

interface MoviesContent {
    @Composable
    fun Content(
        onOpenUrl: (String) -> Boolean,
        modifier: Modifier,
    )
}

@Inject
class DefaultMoviesContent(
    private val appRouteFactory: Set<AppRouteFactory>,
    private val applicationInfo: ApplicationInfo
) : MoviesContent {

    @Composable
    override fun Content(
        onOpenUrl: (String) -> Boolean,
        modifier: Modifier
    ) {

        Napier.base(DebugAntilog())

        setSingletonImageLoaderFactory { context ->
            newImageLoader(context, applicationInfo)
        }

        MaterialTheme {
            MoviesApp(
                routeFactories = appRouteFactory
            )
        }
    }
}

@Composable
fun MoviesApp(routeFactories: Set<AppRouteFactory>) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val adaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()

    Surface {
        MoviesNavigationWrapper(
            adaptiveInfo= adaptiveInfo,
            currentDestination = currentDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo,
        ) {
            RootNavigation(
                navController = navController,
                routeFactories = routeFactories
            )
        }
    }
}

@Composable
fun RootNavigation(
    routeFactories: Set<AppRouteFactory>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier // The modifier passed from the scaffold
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        create(
            factories = routeFactories,
            navController = navController,
        )
    }
}

private fun newImageLoader(
    context: PlatformContext,
    applicationInfo: ApplicationInfo,
): ImageLoader {

    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, percent = 0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(applicationInfo.cachePath().toPath().resolve("coil_cache"))
                .build()
        }
        .apply {
            if (applicationInfo.debugBuild) {
                logger(DebugLogger())
            }
        }
        .build()
}
