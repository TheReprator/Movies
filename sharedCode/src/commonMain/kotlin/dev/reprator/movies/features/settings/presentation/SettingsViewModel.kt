package dev.reprator.movies.features.settings.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import kotlinx.coroutines.FlowPreview
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@OptIn(FlowPreview::class)
@Inject
class SettingsViewModel(
    @Assisted val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

}