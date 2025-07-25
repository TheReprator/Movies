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

package dev.reprator.movies

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.net.toUri
import dev.reprator.movies.di.inject.component.AndroidActivityComponent
import dev.reprator.movies.di.inject.component.AndroidApplicationComponent
import dev.reprator.movies.di.inject.component.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val applicationComponent = AndroidApplicationComponent.from(this)
        val component = AndroidActivityComponent.create(this, applicationComponent)
        setContent {
            component.movieContent.Content(
                onOpenUrl = { url ->
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(this@MainActivity, url.toUri())
                    true
                },
                modifier =
                    Modifier.semantics {
                        // Enables testTag -> UiAutomator resource id
                        @OptIn(ExperimentalComposeUiApi::class)
                        testTagsAsResourceId = true
                    },
            )
        }
    }
}

private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent =
    (context.applicationContext as MoviesApp).component
