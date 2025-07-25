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

fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }

    val winState = rememberWindowState(
        width = 400.dp,
        height = 600.dp,
    )

    Window(
        state = winState,
        onCloseRequest = ::exitApplication,
        title = "Movies",
    ) {

        if(winState.size.width < 350.dp)
            winState.size = DpSize(400.dp,winState.size.height)

        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }

        component.movieContent.Content(
            onOpenUrl = { url ->
                false
            },
            modifier = Modifier
        )
    }
}
