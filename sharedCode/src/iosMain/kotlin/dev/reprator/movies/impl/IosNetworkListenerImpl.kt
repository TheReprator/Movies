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
import platform.Network.nw_interface_type_wifi
import platform.Network.nw_path_get_status
import platform.Network.nw_path_is_constrained
import platform.Network.nw_path_is_expensive
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_uses_interface_type
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create

@Inject
object IosNetworkListenerImpl : NetworkListener {
    val monitor = nw_path_monitor_create()
    val queue =
        dispatch_queue_create(
            label = "dev.reprator.movies.connectivity.monitor",
            attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
        )

    override fun monitor(): Flow<Boolean> =
        callbackFlow {
            nw_path_monitor_set_update_handler(monitor) { path ->
                val status = nw_path_get_status(path)
                when {
                    status == nw_path_status_satisfied -> {
                        val isWifi = nw_path_uses_interface_type(path, nw_interface_type_wifi)
                        val isExpensive = nw_path_is_expensive(path)
                        val isConstrained = nw_path_is_constrained(path)
                        val isMetered = !isWifi && (isExpensive || isConstrained)

                        trySend(isMetered)
                    }
                    else -> trySend(false)
                }
            }

            nw_path_monitor_set_queue(monitor, queue)
            nw_path_monitor_start(monitor)

            awaitClose {
                nw_path_monitor_cancel(monitor)
            }
        }
}
