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

import dev.reprator.movies.BuildConfig
import dev.reprator.movies.di.inject.ApplicationScope
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

private const val MILLISECONDS = 1000L

interface NetworkModule {

    val json: Json

    @ApplicationScope
    @Provides
    fun provideHttpClient(engine: HttpClientEngine): Json {
        return Json {
            prettyPrint = true
            isLenient = true
            useAlternativeNames = true
            ignoreUnknownKeys = true
            encodeDefaults = false
            explicitNulls = false
        }
    }

    @ApplicationScope
    @Provides
    fun provideHttpClient(engine: HttpClientEngine, json: Json): HttpClient {

        return HttpClient(engine) {

            expectSuccess = false

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 10 * MILLISECONDS
                socketTimeoutMillis = 10 * MILLISECONDS
                requestTimeoutMillis = 10 * MILLISECONDS
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d { message }
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Napier.d { response.status.value.toString() }
                }
            }

            install(DefaultRequest) {

                header(HttpHeaders.ContentType, ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                url {
                    protocol = URLProtocol.HTTP
                    host = BuildConfig.BASE_URL
                }
            }

            pluginClientResponseValidator( this)
        }
    }
}