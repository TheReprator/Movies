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

package dev.reprator.movies.impl

import dev.reprator.movies.util.wrapper.NetworkListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import me.tatarka.inject.annotations.Inject
import java.io.IOException
import java.net.InetAddress
import java.net.Socket

@Inject
class DesktopNetworkListenerImpl : NetworkListener {
    override fun monitor(): Flow<Boolean> =
        callbackFlow {
            var socket: Socket? = null
            try {
                // Create a socket to the google DNS server
                socket = Socket(InetAddress.getByName("8.8.8.8"), 53)
                trySend(socket.isConnected.or(false))

                // Close the socket
                socket.close()
            } catch (e: IOException) {
                socket?.close()
            }

            awaitClose {
                socket?.close()
            }
        }
}
