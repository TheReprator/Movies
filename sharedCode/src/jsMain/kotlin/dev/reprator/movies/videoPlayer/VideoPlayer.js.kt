package dev.reprator.movies.videoPlayer

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dev.reprator.movies.videoPlayer.external.VideoJsPlayerOptions
import dev.reprator.movies.videoPlayer.external.videojs
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.*
import kotlinx.browser.document
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLVideoElement

@Composable
actual fun VideoPlayer(url: String, modifier: Modifier) {
    val playerId = remember { "video-js-player-${url.hashCode()}" }

    Div(attrs = {
        id("video-container-$playerId")
        style {
            width(100.percent)
            height(100.percent)
        }
    }) {
        VideoTag(playerId)
    }

    // Setup video.js
    LaunchedEffect(playerId) {
        val options = js("{}").unsafeCast<VideoJsPlayerOptions>()
        options.controls = true
        options.autoplay = false
        options.preload = "auto"

        val videoElement = document.getElementById(playerId)
        if (videoElement != null) {
            val player = videojs(videoElement, options)
            player.src(url)
        }
    }

    // Load video.js CSS
    LaunchedEffect(Unit) {
        val cssHref = "https://vjs.zencdn.net/8.10.0/video-js.css"
        if (document.querySelector("link[href='$cssHref']") == null) {
            val link = document.createElement("link") as? HTMLLinkElement
            if (link != null) {
                link.rel = "stylesheet"
                link.href = cssHref
                document.head?.appendChild(link) // Now safe
            } else {
                console.error("Failed to create HTMLLinkElement.")
            }
        }
    }
}

@Composable
private fun VideoTag(playerId: String) {
    TagElement<HTMLVideoElement>("video", {
        id(playerId)
        classes("video-js")
        attr("playsinline", "true")
        attr("webkit-playsinline", "true")
    }) {}
}
