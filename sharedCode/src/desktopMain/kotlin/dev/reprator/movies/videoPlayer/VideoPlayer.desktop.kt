package dev.reprator.movies.videoPlayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
actual fun VideoPlayer(url: String, modifier: Modifier) {
        VideoPlayerImpl(
            url = url,
            modifier = modifier.fillMaxSize())
}