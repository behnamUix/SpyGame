package com.behnamuix.spy.media.viewmodel

import androidx.lifecycle.ViewModel
import com.behnamuix.spy.media.repo.MediaPlayerRepository

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

    fun isPlaying(): Boolean? {
        return repo.isPlaying()
    }
}