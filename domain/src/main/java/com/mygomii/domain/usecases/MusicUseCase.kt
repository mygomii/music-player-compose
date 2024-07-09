package com.mygomii.domain.usecases

import com.mygomii.data.repositories.MusicRepository
import javax.inject.Inject

class MusicUseCase @Inject constructor(private val musicRepository: MusicRepository) {
    fun music(index: Int) = musicRepository.music(index)
    fun musics() = musicRepository.musics()
}