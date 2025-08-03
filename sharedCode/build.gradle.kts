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


@file:OptIn(
    ExperimentalWasmDsl::class, ExperimentalComposeLibrary::class,
    ExperimentalKotlinGradlePluginApi::class
)

import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import kotlin.sequences.forEach

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            isStatic = true
            baseName = "MoviesApp"
            freeCompilerArgs += "-Xbinary=bundleId=dev.reprator.movies"
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        js(),
        wasmJs()
    ).forEach { target ->
        target.outputModuleName = "MoviesShared"
        target.browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        target.generateTypeScriptDefinitions()
        target.binaries.library()
    }

    sourceSets {
        val desktopMain by getting

        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        androidMain.dependencies {
            implementation(libs.compose.media.exo)
            implementation(libs.compose.media.ui)
            implementation(libs.compose.media.common)
            implementation(libs.compose.media.session)

            implementation(libs.ktor.client.android)

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(libs.androidx.compose.material.icons)

            implementation(libs.coil.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            implementation(libs.logging.napier)
            implementation(libs.kotlininject.runtime)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.client.serialization.json)
            implementation(libs.ktor.client.logging)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(libs.androidx.material3.adaptive)
            implementation(libs.androidx.material3.window)

            implementation(libs.androidx.compose.backhandler)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.test.turbine)
            implementation(compose.uiTest)
        }

        desktopMain.dependencies {
            implementation(libs.compose.media.desktop.vlc)

            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.java)
            implementation(libs.kotlinx.coroutines.swing)
        }

        webMain.dependencies {
            implementation(compose.runtime)
            implementation(libs.ktor.client.js)
            implementation(libs.web.filesystem)
            implementation(npm("video.js", "8.10.0"))
        }

        jsMain.dependencies {
            implementation(compose.html.core)
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("shared_compose_compiler")
    metricsDestination = layout.buildDirectory.dir("shared_compose_metric")
    stabilityConfigurationFiles.addAll(
        project.layout.projectDirectory.file("compose-stability.conf"),
    )
}

dependencies {
    debugImplementation(compose.uiTooling)
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)

android {
    namespace = "dev.reprator.movies.common"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

mokkery {
    ignoreFinalMembers.set(true)
}

fun Project.addKspDependencyForAllTargets(dependencyNotation: Any) =
    addKspDependencyForAllTargets("", dependencyNotation)

fun Project.addKspTestDependencyForAllTargets(dependencyNotation: Any) =
    addKspDependencyForAllTargets("Test", dependencyNotation)

private fun Project.addKspDependencyForAllTargets(
    configurationNameSuffix: String,
    dependencyNotation: Any,
) {
    val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
    dependencies {
        kmpExtension.targets
            .asSequence()
            .filter { target ->
                // Don't add KSP for common target, only final platforms
                target.platformType != KotlinPlatformType.common
            }.forEach { target ->
                add(
                    "ksp${target.targetName.replaceFirstChar { it.uppercaseChar() }}$configurationNameSuffix",
                    dependencyNotation,
                )
            }
    }
}

buildConfig {
    packageName = "dev.reprator.movies"
    useKotlinOutput { internalVisibility = true }
    buildConfigField(
        "String",
        "BASE_URL",
        "\"${project.findProperty("API_BASE_URL")}\"",
    )
}
