package com.mygomii.data.models

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

data class Music(
    val streamUrl: String,
    val imageUrl: String,
    val artists: String,
    val title: String,
    val duration: String,
    var mediaItem: MediaItem? = null
) {
    companion object {
        fun getImage(context: Context, imageUrl: String): Int {
            return context.resources.getIdentifier(imageUrl, "drawable", context.packageName)
        }

    }
}

fun Music.setMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(this.streamUrl)
        .setMediaId(this.title)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setDisplayTitle(this.title)
                .build()
        )
        .build()
}

fun MediaItem.toSong() =
    Music(
        streamUrl = mediaId,
        imageUrl = mediaMetadata.artworkUri.toString(),
        artists = mediaMetadata.subtitle.toString(),
        title = mediaMetadata.title.toString(),
        duration = "",
        mediaItem = this,
    )