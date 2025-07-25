package dev.reprator.movies.di.inject.activity

import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.root.AppRouteFactory
import dev.reprator.movies.root.DefaultMoviesContent
import dev.reprator.movies.root.MoviesContent
import me.tatarka.inject.annotations.Provides

interface SharedModuleComponent :
    AppRouteFactoryComponent {

    val routeFactories: Set<AppRouteFactory>

    val movieContent: MoviesContent

    @Provides
    @ActivityScope
    fun bindMoviesContent(impl: DefaultMoviesContent): MoviesContent = impl
}
