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

package dev.reprator.movies.di.inject.activity

import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.features.home.presentation.ui.HomeRouteFactory
import dev.reprator.movies.features.library.presentation.ui.LibraryRouteFactory
import dev.reprator.movies.features.movies.presentation.ui.MoviesRouteFactory
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
