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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import dev.reprator.movies.di.inject.component.JsApplicationComponent
import dev.reprator.movies.di.inject.component.JsWindowComponent
import dev.reprator.movies.di.inject.component.create
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val body = document.body ?: return@onWasmReady
        ComposeViewport(body) {
            val applicationComponent =
                remember {
                    JsApplicationComponent.create()
                }

            val component =
                remember(applicationComponent) {
                    JsWindowComponent.create(applicationComponent)
                }

            component.movieContent.Content(
                onOpenUrl = { url ->
                    false
                },
                modifier = Modifier,
            )
        }
    }
}
