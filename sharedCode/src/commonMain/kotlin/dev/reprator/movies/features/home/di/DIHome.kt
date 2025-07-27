package dev.reprator.movies.features.home.di

import dev.reprator.movies.features.home.data.movie.MovieDataRepository
import dev.reprator.movies.features.home.data.movie.MovieDataRepositoryImpl
import dev.reprator.movies.features.home.data.repository.remote.MovieRemoteImplRepository
import dev.reprator.movies.features.home.data.repository.remote.TvRemoteImplRepository
import dev.reprator.movies.features.home.data.repository.remote.model.tv.ResponseModelTv
import dev.reprator.movies.features.home.data.repository.remote.model.tv.TvMapper
import dev.reprator.movies.features.home.data.serials.TvDataRepository
import dev.reprator.movies.features.home.data.serials.TvDataRepositoryImpl
import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.features.home.domain.repository.MovieRepository
import dev.reprator.movies.features.home.domain.repository.TvRepository
import dev.reprator.movies.features.home.domain.usecase.MovieUseCase
import dev.reprator.movies.features.home.domain.usecase.MovieUseCaseImpl
import dev.reprator.movies.features.home.domain.usecase.TvUseCase
import dev.reprator.movies.features.home.domain.usecase.TvUseCaseImpl
import dev.reprator.movies.features.home.presentation.HomeAction
import dev.reprator.movies.features.home.presentation.HomeEffect
import dev.reprator.movies.features.home.presentation.HomeMiddleware
import dev.reprator.movies.features.home.presentation.HomeScreenReducer
import dev.reprator.movies.features.home.presentation.HomeState
import dev.reprator.movies.util.api.Mapper
import dev.reprator.movies.util.base.mvi.Middleware
import dev.reprator.movies.util.base.mvi.Reducer
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DIHome {
    @Provides
    fun provideUserListMapper(bind: TvMapper): Mapper<ResponseModelTv, HomeEpisodeOverView>  = bind

    @Provides
    fun provideMovieDataRepository(bind: MovieRemoteImplRepository): MovieDataRepository = bind

    @Provides
    fun provideTvDataRepository(bind: TvRemoteImplRepository): TvDataRepository = bind

    @Provides
    fun provideTvRepository(bind: TvDataRepositoryImpl): TvRepository = bind

    @Provides
    fun provideMovieRepository(bind: MovieDataRepositoryImpl): MovieRepository = bind

    @Provides
    fun provideMovieUseCase(bind: MovieUseCaseImpl): MovieUseCase = bind

    @Provides
    fun provideTVUseCase(bind: TvUseCaseImpl): TvUseCase = bind

    @Provides
    @IntoSet
    fun bindHomeMiddleware(middleWare: HomeMiddleware): Middleware<HomeState, HomeAction, HomeEffect> =
        middleWare

    @Provides
    fun bindHomeScreenReducer(reducer: HomeScreenReducer): Reducer<HomeState, HomeAction, HomeEffect> =
        reducer
}