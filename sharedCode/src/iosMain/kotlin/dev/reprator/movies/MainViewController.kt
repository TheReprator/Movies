package dev.reprator.movies

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.ComposeUIViewController
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController
import dev.reprator.movies.root.AppRouteFactory


typealias MoviesUiViewController = () -> UIViewController

@OptIn(ExperimentalComposeApi::class)
@Inject
fun MoviesUiViewController(
    routeFactories: Set<AppRouteFactory>
): UIViewController = ComposeUIViewController() {
    App(routeFactories)
}
