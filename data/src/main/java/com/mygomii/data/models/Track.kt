package com.mygomii.data.models

import android.content.Context
import androidx.media3.common.MediaItem
import com.mygomii.data.state.PlayerStates

data class Track(
    val id: Int,
    val title: String,
    val uri: String,
    val image: String,
    val artist: String,
    var isSelected: Boolean = false,
    var state: PlayerStates = PlayerStates.STATE_IDLE
)

fun String.getImage(context: Context): Int {
    return context.resources.getIdentifier(this, "drawable", context.packageName)
}

fun List<Track>.toMediaItemList(): MutableList<MediaItem> {
    return this.map { MediaItem.fromUri(it.uri) }.toMutableList()
}

fun MutableList<Track>.resetTracks() {
    this.forEach { track ->
        track.isSelected = false
        track.state = PlayerStates.STATE_IDLE
    }
}