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

package dev.reprator.movies.di.inject.application

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import coil3.disk.DiskCache
import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import dev.reprator.movies.util.wrapper.Platform
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.tatarka.inject.annotations.Provides
import okio.Path.Companion.toPath

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

    @ApplicationScope
    @Provides
    fun provideCoilDiskCache(applicationInfo: ApplicationInfo): DiskCache? =
        DiskCache.Builder()
            .directory(applicationInfo.cachePath().toPath().resolve("coil_cache"))
            .build()
}
