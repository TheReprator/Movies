package dev.reprator.movies.impl

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import dev.reprator.movies.util.wrapper.NetworkListener
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import android.annotation.SuppressLint
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkRequest
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("MissingPermission", "NewApi")
@Inject
class AndroidNetworkListenerImpl(private val context: Application) : NetworkListener {

    private val manager by lazy { context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }

    @SuppressLint("NewApi")
    override fun monitor(): Flow<Boolean> {

        return callbackFlow {
            val networkCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    val capabilities = manager.getNetworkCapabilities(network)
                    val status = manager.status(capabilities)
                    trySend(status)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities,
                ) {
                    val status = manager.status(networkCapabilities)
                    trySend(status)
                }

                override fun onLost(network: Network) {
                    trySend(false)
                }
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.registerDefaultNetworkCallback(networkCallback)
                } else {
                    val networkRequest = NetworkRequest.Builder().build()
                    manager.registerNetworkCallback(networkRequest, networkCallback)
                }

                val initialStatus = manager.initialStatus()
                trySend(initialStatus)

                awaitCancellation()
            } finally {
                manager.unregisterNetworkCallback(networkCallback)
            }
        }
    }

    private fun ConnectivityManager.initialStatus(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activeNetwork?.let { network ->
                getNetworkCapabilities(network)?.let { capabilities ->
                    status(capabilities)
                }
            } ?: false
        } else {
            @Suppress("DEPRECATION")
            val isConnected = activeNetworkInfo?.isConnected == true
            return isConnected
        }
    }

    private fun ConnectivityManager.status(
        capabilities: NetworkCapabilities?,
    ): Boolean {
        val isWifi = capabilities?.hasTransport(TRANSPORT_WIFI) ?: false
        val isCellular = capabilities?.hasTransport(TRANSPORT_CELLULAR) ?: false
        val isMetered = !isWifi || isCellular || isActiveNetworkMetered
        return isMetered
    }
}