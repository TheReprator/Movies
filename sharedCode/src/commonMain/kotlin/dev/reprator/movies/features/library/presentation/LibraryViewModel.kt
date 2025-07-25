package dev.reprator.movies.features.library.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import kotlinx.coroutines.FlowPreview
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@OptIn(FlowPreview::class)
@Inject
class LibraryViewModel(
    @Assisted val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

}