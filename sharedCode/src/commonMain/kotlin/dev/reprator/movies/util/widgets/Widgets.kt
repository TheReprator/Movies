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