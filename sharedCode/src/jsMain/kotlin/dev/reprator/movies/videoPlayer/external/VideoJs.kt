// file: VideoJs.kt (in your jsMain or wasmJsMain source set)
@file:JsModule("video.js")
@file:JsNonModule

package dev.reprator.movies.videoPlayer.external

import kotlin.js.Promise

// Or your preferred package


// Basic options interface (can be expanded)
external interface VideoJsPlayerOptions {
    var controls: Boolean?
    var autoplay: Boolean?
    var preload: String? // "auto", "metadata", "none"
    var sources: Array<VideoJsSource>?
    var width: Int?
    var height: Int?
    // Add other options as needed: https://videojs.com/guides/options/
}

external interface VideoJsSource {
    var src: String
    var type: String?
}

// The video.js Player API (can be greatly expanded)
external interface VideoJsPlayer {
    fun play(): Promise<Unit>? // play() can return a Promise
    fun pause()
    fun dispose()
    fun src(source: dynamic) // Can be a string, an object, or an array of objects
    fun on(event: String, handler: () -> Unit)
    fun ready(callback: () -> Unit)
    // Add other methods and properties as needed: https://docs.videojs.com/player
}

// The main videojs function
// It can take an ID string or an HTML element
external fun videojs(elementIdOrElement: dynamic, options: VideoJsPlayerOptions? = definedExternally, readyCallback: (() -> Unit)? = definedExternally): VideoJsPlayer

/*
@file:JsModule("video.js")
@file:JsNonModule

package dev.reprator.movies.videoPlayer.external
import org.w3c.dom.Element
import kotlin.js.Promise

external fun videojs(
    element: Element,
    options: dynamic = definedExternally,
    ready: (() -> Unit)? = definedExternally
): Player

external interface Player {
    fun play(): Promise<Unit>
    fun pause()
    fun currentTime(): Double
    fun currentTime(seconds: Double)
    fun dispose()
}
*/
