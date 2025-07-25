package dev.reprator.movies.di.inject.component

import dev.reprator.movies.di.inject.ApplicationScope
import dev.reprator.movies.di.inject.application.SharedApplicationComponent
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class IosApplicationComponent(
) : SharedApplicationComponent {

    companion object
}
