package dev.reprator.movies.di.inject.application

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import dev.reprator.movies.util.wrapper.Platform
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.tatarka.inject.annotations.Provides

actual interface SharedPlatformApplicationComponent {

    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Android.create()

    @OptIn(ExperimentalCoroutinesApi::class)
    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        singleThread = Dispatchers.IO.limitedParallelism(1),
        main = Dispatchers.Main.immediate,
    )

    @ApplicationScope
    @Provides
    fun provideApplicationInfo(
        application: Application,
    ): ApplicationInfo {
        val packageManager = application.packageManager
        val applicationInfo = packageManager.getApplicationInfo(application.packageName, 0)
        val packageInfo = packageManager.getPackageInfo(application.packageName, 0)

        return ApplicationInfo(
            packageName = application.packageName,
            debugBuild = (applicationInfo.flags and FLAG_DEBUGGABLE) != 0,
            versionName = packageInfo.versionName.orEmpty(),
            versionCode = @Suppress("DEPRECATION") packageInfo.versionCode,
            cachePath = { application.cacheDir.absolutePath },
            platform = Platform.ANDROID,
        )
    }
}
