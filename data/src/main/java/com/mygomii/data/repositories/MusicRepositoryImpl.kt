package com.mygomii.data.repositories

import com.mygomii.data.getMusics
import com.mygomii.data.models.Music
import com.mygomii.data.models.setMediaItem

class MusicRepositoryImpl : MusicRepository {
    override fun music(index: Int): Music {
        return getMusics[index]
    }

    override fun musics(): List<Music> {
        getMusics.forEach {
            it.mediaItem = it.setMediaItem()
        }

        return getMusics
    }
}