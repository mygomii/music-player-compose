package com.mygomii.presentation.screen.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mygomii.presentation.screen.main.MainViewModel

enum class Screen(val value: String) {
    Main("main"), Detail("detail/{index}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Text(
                        color = Color.White,
                        text = "Player"
                    )
                },
            )
        },
        content = { innerPadding ->
            ContentView(innerPadding = innerPadding)
        }
    )
}


@Composable
fun ContentView(
    viewModel: MainViewModel = hiltViewModel(),
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
            PlayListView(navController, viewModel, innerPadding)
        }

        composable(
            route = Screen.Detail.value,
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: -1
            val music = viewModel.getMusic(index)
            PlayerView(viewModel = viewModel, music, innerPadding)
        }
    }
}
