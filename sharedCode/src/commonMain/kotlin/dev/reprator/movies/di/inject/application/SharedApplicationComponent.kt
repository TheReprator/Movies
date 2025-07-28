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

import dev.reprator.movies.di.inject.ApplicationCoroutineScope
import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.di.inject.application.client.NetworkModule
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import dev.reprator.movies.util.wrapper.ApplicationInfo
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent,
    NetworkModule {
    val dispatchers: AppCoroutineDispatchers
    val httpClient: HttpClient
    val applicationInfo: ApplicationInfo

    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(dispatchers: AppCoroutineDispatchers): ApplicationCoroutineScope =
        CoroutineScope(dispatchers.main + SupervisorJob())
}
