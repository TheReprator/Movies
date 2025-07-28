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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.reprator.movies.features.home.domain.models.CategoryDisplayableItem
import dev.reprator.movies.features.home.domain.models.DisplayableItem
import dev.reprator.movies.features.home.domain.models.DisplayableItemLoader
import dev.reprator.movies.features.home.domain.models.HomeSectionModel
import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.ResultStatus
import dev.reprator.movies.features.home.presentation.HomeAction
import dev.reprator.movies.features.home.presentation.HomeState
import dev.reprator.movies.features.home.presentation.HomeViewModel
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.ui.tooling.preview.Preview

private typealias OnAction = (HomeAction) -> Unit


@Inject
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    var shouldReload by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!shouldReload) {
            homeViewModel.onAction(HomeAction.LoadHomeData)
            shouldReload = true
        }
    }

    HomeScreen(uiState, { action ->
        homeViewModel.onAction(action)
    }, modifier)
}

@Composable
fun HomeScreen(
    homeState: HomeState,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    mainLazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = mainLazyListState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = homeState.sections,
            key = { homeModel -> homeModel.sectionId },
            contentType = { homeModel -> homeModel.layoutType }
        ) { homeModelSection ->
            when (homeModelSection) {
                is HomeSectionModel.GenreChipsSection -> {
                    if (homeModelSection.status == ResultStatus.RESULT_STATUS_RESULT) {
                        GenreChipsSection(homeModelSection.genres, onAction)
                    }
                }

                is HomeSectionModel.ItemCarouselSection -> {
                    SectionContainer(
                        sectionTitle = homeModelSection.categoryName,
                        sectionResultStatus = homeModelSection.status,
                        items = homeModelSection.items,
                        onSectionRetry = { onAction(HomeAction.RetrySection(homeModelSection.sectionId)) },
                        onPaginate = { onAction(HomeAction.CarouselPagination(homeModelSection.sectionId)) }
                    )
                }
            }

            if (homeState.sections.lastOrNull() != homeModelSection) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            }
        }
    }
}

@Composable
fun GenreChipsSection(
    genres: List<MovieGenreItem>,
    onAction: OnAction,
    modifier: Modifier = Modifier,
    lazyState: LazyListState = rememberLazyListState(),
) {
    LazyRow(
        state = lazyState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = genres,
            key = { genre -> genre.id }
        ) { genreItem ->
            GenreChipsItemTile(
                item = genreItem,
                onAction = onAction
            )
        }
    }
}

@Composable
fun GenreChipsItemTile(
    item: MovieGenreItem,
    onAction: OnAction,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = item.isSelected,
        onClick = { onAction(HomeAction.MovieGenreItemSelect(item)) },
        label = {
            Text(
                text = item.name,
                color = if (item.isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant // Or your desired unselected text color
            )
        },
        modifier = modifier.padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.DarkGray.copy(alpha = 0.8f),
            selectedContainerColor = Color.Red
        )
    )
}

@Composable
fun SectionContainer(
    sectionTitle: String,
    sectionResultStatus: ResultStatus,
    items: List<DisplayableItem>,
    onSectionRetry: () -> Unit,
    onPaginate: () -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when (sectionResultStatus) {
            ResultStatus.RESULT_STATUS_LOADER -> {
                WidgetSectionLoader()
            }

            ResultStatus.RESULT_STATUS_EMPTY -> {
                WidgetSectionEmpty("No items found in this section.")
            }

            ResultStatus.RESULT_STATUS_ERROR -> {
                WidgetSectionError("Failed to load this section.", onSectionRetry)
            }

            ResultStatus.RESULT_STATUS_RESULT -> {
                val lazyListState: LazyListState = rememberLazyListState()

                val shouldLoadMore by remember {
                    derivedStateOf {
                        val layoutInfo = lazyListState.layoutInfo
                        val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        val totalItemsCount = layoutInfo.totalItemsCount
                        lastVisibleItemIndex != null && totalItemsCount > 0 &&
                                lastVisibleItemIndex >= totalItemsCount - 3 // Threshold
                    }
                }

                LaunchedEffect(shouldLoadMore) {
                    if (shouldLoadMore && items.lastOrNull() !is DisplayableItemLoader) {
                        onPaginate()
                    }
                }

                RenderCarouselItem(items, onSectionRetry, lazyListState = lazyListState)
            }
        }
    }
}

@Composable
fun RenderCarouselItem(
    items: List<DisplayableItem>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = items,
            key = { it.id },
            contentType = { item -> item.itemType }
        ) { itemData ->
            RenderItemType(
                item = itemData,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun RenderItemType(item: DisplayableItem, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    when (item.itemType) {
        ResultStatus.RESULT_STATUS_LOADER -> {
            WidgetItemLoader(modifier)
        }

        ResultStatus.RESULT_STATUS_EMPTY -> {
            WidgetItemEmpty(modifier)
        }

        ResultStatus.RESULT_STATUS_ERROR -> {
            WidgetItemError(onRetry = onRetry, modifier = modifier)
        }

        ResultStatus.RESULT_STATUS_RESULT -> {
            ActualContentItemCard(item = item as CategoryDisplayableItem, modifier = modifier)
        }
    }
}


@Composable
fun WidgetSectionLoader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        Text("Loading Section...", modifier = Modifier.padding(top = 60.dp))
    }
}

@Composable
fun WidgetSectionEmpty(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp), // Example height
        contentAlignment = Alignment.Center
    ) {
        Text("Section Empty: $message")
    }
}

@Composable
fun WidgetSectionError(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp), // Example height
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Section Error: $message")
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun WidgetItemLoader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(120.dp) // Example width for a poster
            .height(180.dp) // Example height for a poster
            .background(Color.Gray.copy(alpha = 0.5f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(30.dp))
    }
}

@Composable
fun WidgetItemEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(120.dp)
            .height(180.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("N/A", fontSize = 10.sp)
    }
}

@Composable
fun WidgetItemError(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(120.dp)
            .height(180.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onRetry, modifier = Modifier.scale(0.8f)) {
            Text("Retry", fontSize = 10.sp)
        }
    }
}

@Composable
fun ActualContentItemCard(item: CategoryDisplayableItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(120.dp)
            .height(180.dp)
    ) {
        Box {
            AsyncImage(
                model = item.posterImage,
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(modifier = Modifier.align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "⭐${item.ratings}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}

@Preview
@Composable
fun GenreChipsSectionPreview() {
    val itemList = listOf(MovieGenreItem("1", "Action"), MovieGenreItem("2", "Drama", true))
    GenreChipsSection(genres = itemList, onAction = {})
}