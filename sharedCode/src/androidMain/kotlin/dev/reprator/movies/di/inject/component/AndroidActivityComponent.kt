package dev.reprator.movies.di.inject.component

import android.app.Activity
import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.di.inject.activity.SharedModuleComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScope
@Component
abstract class AndroidActivityComponent(
    @get:Provides val activity: Activity,
    @Component val applicationComponent: AndroidApplicationComponent,
): SharedModuleComponent {
    companion object
}