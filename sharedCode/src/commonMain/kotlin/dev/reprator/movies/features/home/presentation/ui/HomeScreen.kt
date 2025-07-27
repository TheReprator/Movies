/*
 * Copyright 2025 @TheReprator
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.reprator.movies.features.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.reprator.movies.features.home.domain.models.CategoryItem
import dev.reprator.movies.features.home.domain.models.HomeModel
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.ResultStatus
import dev.reprator.movies.features.home.presentation.HomeAction
import dev.reprator.movies.features.home.presentation.HomeState
import dev.reprator.movies.features.home.presentation.HomeViewModel
import dev.reprator.movies.util.widgets.WidgetEmpty
import dev.reprator.movies.util.widgets.WidgetLoader
import dev.reprator.movies.util.widgets.WidgetRetry
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Inject


private typealias OnAction = (HomeAction) -> Unit

@Inject
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(homeViewModel) {
        homeViewModel.onAction(HomeAction.UpdateHomeList)
    }

    Napier.e { "HomeScreen ${uiState.itemList}" }
    HomeScreen(uiState, {
        homeViewModel.onAction(it)
    }, modifier)
}

@Composable
fun HomeScreen(
    state: HomeState,
    action: OnAction,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    Napier.e { "HomeScreen1 ${state.itemList}" }
    LazyColumn(state = lazyListState, modifier = modifier) {
        items(state.itemList, key = { it.id }, contentType = { it.resultStatus }) { item ->
            when (item) {
                is HomeModel.ModelGenre -> {
                    GenreItemContainer(state = item)
                }

                is HomeModel.ModelTvSeries -> {
                    this@LazyColumn.SerialItemContainer(state = item, action = action)
                }

                is HomeModel.ModelMovie -> {
                    this@LazyColumn.MovieItemContainer(state = item, action)
                }
            }
        }
    }
}

@Composable
fun GenreItemContainer(
    state: HomeModel.ModelGenre,
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState()
) {
    if (state.resultStatus != ResultStatus.RESULT_STATUS_RESULT) {
        return
    }

    LazyRow(state = lazyState, modifier = Modifier) {
        items(state.genreList, key = { it.id }) { item ->
            GenreItem(item = item) {

            }
        }
    }
}

@Composable
fun GenreItem(
    modifier: Modifier = Modifier,
    item: MovieGenreItem,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(36.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(item.name, fontSize = 14.sp)
    }
}

@Composable
fun HomeDivider() {
    HorizontalDivider(thickness = 12.dp)
}

@Composable
fun LazyListScope.MovieItemContainer(
    state: HomeModel.ModelMovie,
    action: OnAction,
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState()
) {
    Napier.e { "HomeScreen MovieItemContainer ${state.itemList}" }
    item {
        Text(text = state.categoryName)
        HomeDivider()
    }

    when (state.resultStatus) {
        ResultStatus.RESULT_STATUS_LOADER -> {
            item {
                WidgetLoader()
            }
        }

        ResultStatus.RESULT_STATUS_ERROR -> {
            item {
                WidgetRetry(onRetry = {
                    action(HomeAction.RetryMovie)
                })
            }
        }

        ResultStatus.RESULT_STATUS_EMPTY -> {
            item {
                WidgetEmpty("No Data Found")
            }
        }

        ResultStatus.RESULT_STATUS_RESULT -> {
            item {
                HomPosterContainer(
                    state.itemList as List<CategoryItem>, callRetry = {},
                    lazyState = lazyState
                )
            }
        }
    }
}

@Composable
fun HomPosterContainer(
    itemList: List<CategoryItem>,
    modifier: Modifier = Modifier,
    callRetry: () -> Unit,
    lazyState: LazyListState = rememberLazyListState()
) {
    LazyRow(state = lazyState, modifier = Modifier) {
        items(itemList, key = {
            it.id
        }, contentType = {
            it.itemType
        }) { data ->
            when (data.itemType) {
                ResultStatus.RESULT_STATUS_LOADER -> {
                    Box(modifier = Modifier.wrapContentWidth()) {
                        WidgetLoader()
                    }
                }

                ResultStatus.RESULT_STATUS_ERROR -> {
                    Box(modifier = Modifier.wrapContentWidth()) {
                        WidgetRetry(onRetry = callRetry)
                    }
                }

                ResultStatus.RESULT_STATUS_EMPTY -> {

                }

                ResultStatus.RESULT_STATUS_RESULT -> {
                    HomPosterListItem(data)
                }
            }
        }
    }
}

@Composable
fun HomPosterListItem(
    itemList: CategoryItem,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier.requiredSizeIn(120.dp, 160.dp)) {
        Box {
            AsyncImage(
                model = itemList.posterImage, contentDescription = itemList.name,
                modifier = Modifier.fillMaxSize()
            )
            Text(text = itemList.name, Modifier.align(Alignment.BottomStart))
            Text(text = itemList.ratings, Modifier.align(Alignment.BottomEnd))
        }
    }
}


@Composable
fun LazyListScope.SerialItemContainer(
    state: HomeModel.ModelTvSeries,
    modifier: Modifier = Modifier,
    action: OnAction,
    lazyState: LazyListState = rememberLazyListState()
) {

    when (state.resultStatus) {
        ResultStatus.RESULT_STATUS_LOADER -> {
            item {
                WidgetLoader()
            }
        }

        ResultStatus.RESULT_STATUS_ERROR -> {
            item {
                WidgetRetry(onRetry = {
                    action(HomeAction.RetryTv)
                })
            }
        }

        ResultStatus.RESULT_STATUS_EMPTY -> {
            item {
                WidgetEmpty("No Data Found")
            }
        }

        ResultStatus.RESULT_STATUS_RESULT -> {
            item {
                HomPosterContainer(
                    state.itemList as List<CategoryItem>, callRetry = {},
                    lazyState = lazyState
                )
            }
        }
    }
}