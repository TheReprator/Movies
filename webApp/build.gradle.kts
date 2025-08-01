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


@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    listOf(
        js(),
        wasmJs()
    ).forEach { target ->
        target.generateTypeScriptDefinitions()
        target.outputModuleName.set("movies")
        target.binaries.executable()

        target.browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                sourceMaps = true
                outputFileName = "movies.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                    port = 8081
                }
            }
        }
    }

    sourceSets {
        val webMain by creating {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(projects.sharedCode)
            }
        }

        val jsMain by getting {
            dependsOn(webMain)
            dependencies {
                implementation(compose.html.core)
            }
        }

        val wasmJsMain by getting {
            dependsOn(webMain)
        }
    }
}