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

package dev.reprator.movies.util.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import movies.sharedcode.generated.resources.Res
import movies.sharedcode.generated.resources.app_error_generic
import movies.sharedcode.generated.resources.app_retry
import movies.sharedcode.generated.resources.test_tag_loader
import org.jetbrains.compose.resources.stringResource

@Composable
fun WidgetRetry(
    error: String? = null,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            onRetry()
        }) {
            Text(text = stringResource(Res.string.app_retry), modifier = Modifier.padding(10.dp))
        }
        Text(text = error ?: stringResource(Res.string.app_error_generic), textAlign = TextAlign.Center,)
    }
}

@Composable
fun WidgetLoader(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(Modifier.testTag(stringResource(Res.string.test_tag_loader)))
}

@Composable
fun WidgetEmpty(message: String) {
    Text(message)
}