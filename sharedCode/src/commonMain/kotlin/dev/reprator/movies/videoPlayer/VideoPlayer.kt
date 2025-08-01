package dev.reprator.movies.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(url: String, modifier: Modifier)