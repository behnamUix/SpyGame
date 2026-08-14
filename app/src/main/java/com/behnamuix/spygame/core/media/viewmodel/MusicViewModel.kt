package com.behnamuix.spygame.core.media.viewmodel

import androidx.lifecycle.ViewModel
import com.behnamuix.spygame.core.controller.MusicController

class MediaPlayerViewModel(private val controller: MusicController) : ViewModel() {

    fun playBackgroundMusic(url: String) {
        controller.play(url)
    }

    fun pauseMusic() {
        controller.pause()
    }

    fun stopMusic() {
        controller.stop()
    }

    fun setMusicVolume(volume: Float) {
        controller.setVolume(volume)
    }


}
