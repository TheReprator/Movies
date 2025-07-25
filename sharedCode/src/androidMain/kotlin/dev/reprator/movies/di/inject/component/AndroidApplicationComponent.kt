package dev.reprator.movies.di.inject.component

import android.app.Activity
import android.app.Application
import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.di.inject.application.SharedApplicationComponent
import dev.reprator.movies.util.wrapper.NetworkListener
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val activity: Activity, @get:Provides val application: Application,
) : SharedApplicationComponent {

    abstract val networkListener: NetworkListener

    companion object
}
