package com.mygomii.music_player_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mygomii.music_player_compose.ui.theme.MusicplayercomposeTheme
import com.mygomii.presentation.screen.screens.Main
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicplayercomposeTheme {
                Main()
            }
        }
    }
}