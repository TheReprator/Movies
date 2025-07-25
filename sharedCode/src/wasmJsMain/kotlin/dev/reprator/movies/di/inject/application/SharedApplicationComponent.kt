package dev.reprator.movies.di.inject.application

import dev.reprator.movies.di.inject.ApplicationScope

import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import dev.reprator.movies.util.wrapper.Platform
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {

    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Js.create()

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.Default,
        singleThread = Dispatchers.Default,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideApplicationId(): ApplicationInfo = ApplicationInfo(
        packageName = "dev.reprator.accountbook",
        debugBuild = true,
        versionName = "1.0.0",
        versionCode = 1,
        platform = Platform.WEB_WASM,
        cachePath = { "" })
}
