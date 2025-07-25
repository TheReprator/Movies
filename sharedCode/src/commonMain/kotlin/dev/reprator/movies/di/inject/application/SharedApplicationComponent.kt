package dev.reprator.movies.di.inject.application

import dev.reprator.movies.di.inject.application.client.NetworkModule
import dev.reprator.movies.di.inject.ApplicationCoroutineScope
import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent, NetworkModule {

    val dispatchers: AppCoroutineDispatchers
    val httpClient: HttpClient
    val applicationInfo: ApplicationInfo

    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): ApplicationCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}