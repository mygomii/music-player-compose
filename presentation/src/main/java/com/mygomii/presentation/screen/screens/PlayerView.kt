package com.mygomii.presentation.screen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mygomii.data.models.Music
import com.mygomii.presentation.R
import com.mygomii.presentation.screen.main.MainViewModel
import com.mygomii.presentation.screen.screens.components.Cover
import com.mygomii.presentation.screen.screens.components.Singer
import com.mygomii.presentation.screen.screens.components.Title
import com.orhanobut.logger.Logger

@Composable
fun PlayerView(viewModel: MainViewModel, music: Music, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Cover(imageUrl = music.imageUrl)
        Spacer(modifier = Modifier.padding(12.dp))

        Title(title = music.title)
        Singer(singer = music.artists)

        Spacer(modifier = Modifier.padding(24.dp))
        ControlView(viewModel, music)
    }
}


@Composable
fun Heart() {
    Icon(
        imageVector = Icons.Sharp.Favorite,
        tint = Color.White,
        contentDescription = "",
        modifier = Modifier
            .size(18.dp)

    )
}

@Composable
fun ControlView(viewModel: MainViewModel, music: Music) {
    val state = viewModel.homeState.value

    Row {
        Image(
            painter = painterResource(id = R.drawable.prev),
            contentDescription = "Play",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(60.dp)
                .align(Alignment.CenterVertically)
                .clickable {

                })

        if (state.isPlaying) {
            Image(
                painter = painterResource(id = R.drawable.pause),
                contentDescription = "Play",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(60.dp)
                    .clickable {
                        viewModel.playAndPause()
                    })
        } else {
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(60.dp)
                    .clickable {
                        Logger.d("######## clickable")
                        viewModel.prepare(music)
                    })
        }

        Image(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "Play",
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(60.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                })

    }
}
