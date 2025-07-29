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

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController
import dev.reprator.movies.root.MoviesContent
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController

typealias MoviesUiViewController = () -> UIViewController

@OptIn(ExperimentalComposeApi::class)
@Inject
fun MoviesUiViewController(
    rootContent: MoviesContent,
): UIViewController = ComposeUIViewController() {
    val uiViewController = LocalUIViewController.current
    rootContent.Content(

        onOpenUrl = { url ->
            val safari = SFSafariViewController(NSURL(string = url))
            uiViewController.presentViewController(safari, animated = true, completion = null)
            true
        },
        modifier = Modifier,
    )
}
