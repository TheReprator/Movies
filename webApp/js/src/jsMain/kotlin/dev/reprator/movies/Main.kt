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

            val applicationComponent = remember {
                JsApplicationComponent.create()
            }

            val component = remember(applicationComponent) {
                JsWindowComponent.create(applicationComponent)
            }

            component.movieContent.Content(
                onOpenUrl = { url ->
                    false
                },
                modifier = Modifier
            )
        }
    }
}