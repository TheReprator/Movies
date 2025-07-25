package dev.reprator.movies.di.inject.component

import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.di.inject.activity.SharedModuleComponent
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class JsWindowComponent(
    @Component val applicationComponent: JsApplicationComponent,
) : SharedModuleComponent {

    companion object
}