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


import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.gradle.ComposeHotRun
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.hotReload)
}

private val packageName = "dev.reprator.movies"

kotlin {
    jvm {
        @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.sharedCode)
            implementation(compose.desktop.currentOs)
        }
    }
}

tasks.withType<ComposeHotRun>().configureEach {
    mainClass = "$packageName.MainKt"
}

compose.desktop {
    application {
        mainClass = "$packageName.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = packageName
            packageVersion = "1.0.0"
            includeAllModules = true

            linux {
                iconFile.set(project.file("resources/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("resources/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("resources/MacosIcon.icns"))
                bundleID = "dev.reprator.movies.desktopApp"
            }
        }

        buildTypes.release.proguard {
            configurationFiles.from("compose-desktop.pro")
        }
    }
}
