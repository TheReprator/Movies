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

package dev.reprator.movies.features.movies.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.reprator.movies.features.home.presentation.HomeViewModel
import dev.reprator.movies.videoPlayer.VideoPlayer
import me.tatarka.inject.annotations.Inject


@Inject
@Composable
fun MoviesScreen(
    userListViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Text("Movie Screen")

    Column(modifier = Modifier.fillMaxWidth().height(400.dp).width(300.dp)) {

        VideoPlayer(
            url =
                "http://sample.vodobox.com/planete_interdite/planete_interdite_alternate.m3u8"
        )
    }
    /*
    *  Column {
        Button(onClick = {
            scope.launch {
                player.playUri("http://sample.vodobox.com/planete_interdite/planete_interdite_alternate.m3u8")
            }
        }) {
            Text("Play")
        }

        MediampPlayerSurface(player, Modifier.fillMaxSize())
    }
    * */
}