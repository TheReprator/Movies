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

    override fun monitor(): Flow<Boolean> {
        return callbackFlow {
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

}