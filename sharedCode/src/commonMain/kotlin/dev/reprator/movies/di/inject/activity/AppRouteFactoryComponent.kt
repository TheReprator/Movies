package dev.reprator.movies.di.inject.activity

import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.features.home.presentation.ui.HomeRouteFactory
import dev.reprator.movies.features.movies.presentation.ui.MoviesRouteFactory
import dev.reprator.movies.features.library.presentation.ui.LibraryRouteFactory
import dev.reprator.movies.features.settings.presentation.ui.SettingsRouteFactory
import dev.reprator.movies.root.AppRouteFactory
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface AppRouteFactoryComponent {
    @IntoSet
    @ActivityScope
    @Provides
    fun bindHomeRouteFactory(bind: HomeRouteFactory): AppRouteFactory = bind

    @IntoSet
    @ActivityScope
    @Provides
    fun bindMovieRouteFactory(bind: MoviesRouteFactory): AppRouteFactory = bind

    @IntoSet
    @ActivityScope
    @Provides
    fun bindSerialRouteFactory(bind: LibraryRouteFactory): AppRouteFactory = bind

    @IntoSet
    @ActivityScope
    @Provides
    fun bindSettingRouteFactory(bind: SettingsRouteFactory): AppRouteFactory = bind
}