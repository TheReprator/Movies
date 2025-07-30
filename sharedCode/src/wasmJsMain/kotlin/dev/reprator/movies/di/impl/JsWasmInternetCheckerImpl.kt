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

package dev.reprator.movies.di.impl

import dev.reprator.movies.util.wrapper.NetworkListener
import kotlinx.browser.window
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.events.Event
import kotlin.isInitialized

private const val APP_EVENT_OFFLINE = "offline"
private const val APP_EVENT_ONLINE = "online"

@Inject
class JsWasmInternetCheckerImpl : NetworkListener {
    lateinit var callback: (event: Event) -> Unit

    private fun getCurrentNetworkState() =
        when {
            window.navigator.onLine -> true
            else -> false
        }

    override fun monitor(): Flow<Boolean> =
        callbackFlow {
            if (!::callback.isInitialized) {
                callback = { _ ->
                    val networkState = getCurrentNetworkState()
                    trySend(networkState)
                }
            }

            window.addEventListener(APP_EVENT_ONLINE, callback)
            window.addEventListener(APP_EVENT_OFFLINE, callback)

            window.dispatchEvent(Event(APP_EVENT_ONLINE))

            awaitClose {
                window.removeEventListener(APP_EVENT_ONLINE, callback)
                window.removeEventListener(APP_EVENT_OFFLINE, callback)
            }
        }
}
