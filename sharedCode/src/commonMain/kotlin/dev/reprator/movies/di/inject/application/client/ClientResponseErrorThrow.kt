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

package dev.reprator.movies.di.inject.application.client

import dev.reprator.movies.util.CustomHttpExceptions
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*

fun pluginClientResponseValidator(httpClientConfig: HttpClientConfig<*>) {

    httpClientConfig.HttpResponseValidator {
        validateResponse { response ->

            if (!response.status.isSuccess()) {
                val failureReason = when (response.status) {
                    HttpStatusCode.Unauthorized -> "Unauthorized request"
                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                    HttpStatusCode.NotFound -> "Invalid Request"
                    HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                    HttpStatusCode.RequestTimeout -> "Network Timeout"
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout ->
                        "${response.status.value} Server Error"

                    else -> "Network error!"
                }
                Napier.e { failureReason }

                throw mapResponse(response, failureReason)
            }
        }

        handleResponseExceptionWithRequest { exception, _ ->
            val clientException =
                exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest

            val exceptionResponse = clientException.response

            Napier.e { exceptionResponse.toString() }

            throw mapResponse(exceptionResponse)
        }
    }
}


suspend fun mapResponse( response: HttpResponse, failureReason: String ?= null): Exception {
    return CustomHttpExceptions(
        response = response,
        failureReason = failureReason ?: response.bodyAsText(),
        cachedResponseText = response.bodyAsText(),
    )
}