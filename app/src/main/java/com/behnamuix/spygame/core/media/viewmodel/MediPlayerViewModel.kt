package com.behnamuix.spygame.core.media.viewmodel

import androidx.lifecycle.ViewModel
import com.behnamuix.spygame.core.media.repo.MediaPlayerRepository

class MediaPlayerViewModel(private val repo: MediaPlayerRepository) : ViewModel() {

    fun play() {
        repo.play()
    }

    fun pause() {
        repo.pause()
    }

    fun stop() {

        repo.stop()
    }

    fun release() {
        repo.release()

    }

    fun isPlaying(): Boolean {
        return repo.isPlaying()
    }

    fun volumeHigh() {
        repo.setVolume(1f, 1f)
    }

    fun volumeLow() {
        repo.setVolume(0.3f, 0.3f)
    }



}
