package com.mygomii.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mygomii.data.models.Track
import com.mygomii.data.state.PlayerEvent
import com.mygomii.presentation.screens.components.Cover
import com.mygomii.presentation.screens.components.NextIcon
import com.mygomii.presentation.screens.components.PlayPauseIcon
import com.mygomii.presentation.screens.components.PreviousIcon
import com.mygomii.presentation.screens.components.Title
import com.mygomii.presentation.screens.components.TrackListItem
import com.mygomii.presentation.screens.main.PlayViewModel

@Composable
fun Home(navController: NavHostController, viewModel: PlayViewModel, paddingValues: PaddingValues) {
    val onBottomTabClick: () -> Unit = {
        navController.navigate("detail/${viewModel.selectedTrack!!.id}")
    }

    TrackList(
        navController,
        tracks = viewModel.tracks,
        selectedTrack = viewModel.selectedTrack,
        playerEvents = viewModel,
        paddingValues = paddingValues,
        onBottomTabClick = onBottomTabClick
    )
}


@Composable
fun TrackList(
    navController: NavHostController,
    tracks: List<Track>,
    selectedTrack: Track?,
    playerEvents: PlayerEvent,
    paddingValues: PaddingValues,
    onBottomTabClick: () -> Unit
) {
    Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize()
                    .background(Color.Black),
                contentPadding = PaddingValues(5.dp)
            ) {

                items(tracks.size) { index ->
                    TrackListItem(
                        track = tracks[index],
                        onTrackClick = {
                            playerEvents.onTrackClick(tracks[index])
                            navController.navigate("detail/${index}")
                        })
                }
            }
            AnimatedVisibility(
                modifier = Modifier.background(Color.Transparent),
                visible = selectedTrack != null,
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight })
            ) {
                BottomPlayerTab(selectedTrack!!, playerEvents, onBottomTabClick)
            }
        }
    }
}


@Composable
fun BottomPlayerTab(
    selectedTrack: Track,
    playerEvents: PlayerEvent,
    onBottomTabClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .clickable(onClick = onBottomTabClick)
            .padding(all = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Cover(selectedTrack.image, 70.dp)

            Title(title = selectedTrack.title, fontSize = 12.sp)

            PreviousIcon(onClick = playerEvents::onPreviousClick)

            PlayPauseIcon(
                selectedTrack = selectedTrack,
                onClick = playerEvents::onPlayPauseClick,
            )

            NextIcon(onClick = playerEvents::onNextClick)
        }
    }
}