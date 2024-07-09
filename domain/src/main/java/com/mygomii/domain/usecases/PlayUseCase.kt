package com.mygomii.domain.usecases

import androidx.media3.common.MediaItem
import com.mygomii.data.repositories.PlayRepository
import com.mygomii.data.state.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayUseCase @Inject constructor(private val playRepository: PlayRepository) {
    suspend fun invoke(mediaItem: MediaItem) = playRepository.play(mediaItem)
}

class PauseUseCase @Inject constructor(private val playRepository: PlayRepository) {
    suspend operator fun invoke() = playRepository.playAndPause()
}

class PlayerStateUseCase @Inject constructor(private val playRepository: PlayRepository) {
    suspend operator fun invoke(): MutableStateFlow<PlayerState> = playRepository.state()
}

//class NextPlayUseCase @Inject constructor(private val playRepository: PlayRepository) {
//    operator fun invoke() = playRepository.next()
//}
//
//class PreviousPlayUseCase @Inject constructor(private val playRepository: PlayRepository) {
//    operator fun invoke() = playRepository.previous()
//}
