package com.mygomii.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mygomii.presentation.screens.main.PlayViewModel

enum class Screen(val value: String) {
    Main("main"), Detail("detail/{index}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    Scaffold(topBar = {
        Surface(shadowElevation = 5.dp) {
            TopAppBar(
                title = {
                    Text(
                        color = Color.White,
                        text = "Player",
                        style = typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
            )
        }
    }) { paddingValues ->
        ContentView(innerPadding = paddingValues)
    }
}


@Composable
fun ContentView(
    viewModel: PlayViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.value,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(route = Screen.Main.value) {
            Home(navController, viewModel, paddingValues = innerPadding)
        }

        composable(
            route = Screen.Detail.value,
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) {
            if (viewModel.selectedTrack != null) TrackView(
                selectedTrack = viewModel.selectedTrack!!,
                playerEvents = viewModel,
                playbackState = viewModel.playbackState,
                paddingValues = innerPadding
            )
        }
    }
}
