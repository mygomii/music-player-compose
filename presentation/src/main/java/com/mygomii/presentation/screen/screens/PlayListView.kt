package com.mygomii.presentation.screen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mygomii.data.models.Music
import com.mygomii.domain.state.UiState
import com.mygomii.presentation.screen.main.MainViewModel
import com.mygomii.presentation.screen.screens.components.Cover
import com.mygomii.presentation.screen.screens.components.Singer
import com.mygomii.presentation.screen.screens.components.Title

@Composable
fun PlayListView(navController: NavHostController, viewModel: MainViewModel = hiltViewModel(), innerPadding: PaddingValues) {
    val screenState = viewModel.screenStateStream.collectAsState().value

    when (screenState) {
        is UiState.Loading -> Loading()
        is UiState.Success -> Loaded(navController, screenState = screenState, innerPadding)
        is UiState.Failure -> Text("failure")

    }
}

@Composable
fun Loading() {

}

@Composable
fun Loaded(navController: NavHostController, screenState: UiState.Success, paddingValues: PaddingValues) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        items(screenState.musics.size) { index ->
            ItemView(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("detail/${index}")
                    },
                screenState.musics[index],
            )

        }
    }
}

@Composable
fun ItemView(modifier: Modifier, music: Music) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cover(music.imageUrl, 60.dp)

        Column {
            Title(title = music.title)
            Singer(singer = music.artists)
        }
    }
}

