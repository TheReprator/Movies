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

import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import dev.reprator.movies.util.wrapper.Platform
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Provides
import java.io.File

actual interface SharedPlatformApplicationComponent {
    @ApplicationScope
    @Provides
    fun provideApplicationId(): ApplicationInfo =
        ApplicationInfo(
            packageName = "dev.reprator.movies",
            debugBuild = true,
            versionName = "1.0.0",
            versionCode = 1,
            cachePath = { getCacheDir().absolutePath },
            platform = Platform.DESKTOP,
        )

    @Provides
    @ApplicationScope
    fun provideHttpClientEngine(): HttpClientEngine = Java.create()

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers =
        AppCoroutineDispatchers(
            io = Dispatchers.IO,
            singleThread = Dispatchers.IO.limitedParallelism(1),
            computation = Dispatchers.Default,
            main = Dispatchers.Main,
        )
}

private fun getCacheDir(): File =
    when (currentOperatingSystem) {
        OperatingSystem.Windows -> File(System.getenv("AppData"), "movies/cache")
        OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/movies")
        OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/movies")
        else -> throw IllegalStateException("Unsupported operating system")
    }

internal enum class OperatingSystem {
    Windows,
    Linux,
    MacOS,
    Unknown,
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> OperatingSystem.Windows
            os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                OperatingSystem.Linux
            }

            os.contains("mac") -> OperatingSystem.MacOS
            else -> OperatingSystem.Unknown
        }
    }
