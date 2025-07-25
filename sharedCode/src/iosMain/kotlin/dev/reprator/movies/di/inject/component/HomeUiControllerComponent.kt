package dev.reprator.movies.di.inject.component

import dev.reprator.movies.GithubUiViewController
import dev.reprator.movies.di.inject.ActivityScope
import dev.reprator.movies.di.inject.activity.SharedModuleComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class HomeUiControllerComponent(
    @Component val applicationComponent: IosApplicationComponent,
) : SharedModuleComponent {

    abstract val uiViewControllerFactory: () -> UIViewController

    @Provides
    @ActivityScope
    fun uiViewController(bind: GithubUiViewController): UIViewController = bind()

    companion object
}
