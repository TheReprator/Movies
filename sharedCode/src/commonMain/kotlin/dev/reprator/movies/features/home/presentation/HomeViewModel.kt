package dev.reprator.movies.features.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.reprator.movies.util.wrapper.AppCoroutineDispatchers
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

const val SEARCH_QUERY_KEY = "searchQuery"
private const val MINIMUM_SEARCH_DEBOUNCE = 100L
private const val MINIMUM_SEARCH_LENGTH = 3

@OptIn(FlowPreview::class)
@Inject
class HomeViewModel(
    @Assisted val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

}