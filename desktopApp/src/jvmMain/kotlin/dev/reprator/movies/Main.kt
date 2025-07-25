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

package dev.reprator.movies

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.reprator.movies.di.inject.component.DesktopApplicationComponent
import dev.reprator.movies.di.inject.component.WindowComponent
import dev.reprator.movies.di.inject.component.create

fun main() =
    application {
        val applicationComponent =
            remember {
                DesktopApplicationComponent.create()
            }

        val winState =
            rememberWindowState(
                width = 400.dp,
                height = 600.dp,
            )

        Window(
            state = winState,
            onCloseRequest = ::exitApplication,
            title = "Movies",
        ) {
            if (winState.size.width < 350.dp) {
                winState.size = DpSize(400.dp, winState.size.height)
            }

            val component =
                remember(applicationComponent) {
                    WindowComponent.create(applicationComponent)
                }

            component.movieContent.Content(
                onOpenUrl = { url ->
                    false
                },
                modifier = Modifier,
            )
        }
    }
