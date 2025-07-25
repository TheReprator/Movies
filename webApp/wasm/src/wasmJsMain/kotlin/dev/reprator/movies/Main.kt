package dev.reprator.movies

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import dev.reprator.movies.di.inject.component.WasmJsApplicationComponent
import dev.reprator.movies.di.inject.component.WasmJsWindowComponent
import dev.reprator.movies.di.inject.component.create
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {

        val applicationComponent = remember {
            WasmJsApplicationComponent.create()
        }

        val component = remember(applicationComponent) {
            WasmJsWindowComponent.create(applicationComponent)
        }

        component.movieContent.Content(
            onOpenUrl = { url ->
                false
            },
            modifier = Modifier
        )
    }
}