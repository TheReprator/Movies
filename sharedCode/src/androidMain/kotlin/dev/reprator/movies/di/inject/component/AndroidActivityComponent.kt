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

package dev.reprator.movies.di.inject.component

import android.app.Activity
import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.di.inject.activity.SharedModuleComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class AndroidActivityComponent(
    @get:Provides val activity: Activity,
    @Component val applicationComponent: AndroidApplicationComponent,
) : SharedModuleComponent {
    companion object
}
