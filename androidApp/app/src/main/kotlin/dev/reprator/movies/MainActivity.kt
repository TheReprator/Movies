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
import dev.reprator.movies.di.inject.component.AndroidActivityComponent
import dev.reprator.movies.di.inject.component.AndroidApplicationComponent
import dev.reprator.movies.di.inject.component.create
import androidx.core.net.toUri

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
                modifier = Modifier.semantics {
                    // Enables testTag -> UiAutomator resource id
                    @OptIn(ExperimentalComposeUiApi::class)
                    testTagsAsResourceId = true
                },
            )
        }
    }
}

private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    return (context.applicationContext as MoviesApp).component
}