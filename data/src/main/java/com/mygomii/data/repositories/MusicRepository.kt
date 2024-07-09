package com.mygomii.data.repositories

import com.mygomii.data.models.Music

interface MusicRepository {
    fun music(index: Int): Music
    fun musics(): List<Music>
}