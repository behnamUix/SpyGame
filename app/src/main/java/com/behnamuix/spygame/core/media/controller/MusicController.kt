package com.behnamuix.spygame.core.controller


import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer


class MusicController(
    private val player: ExoPlayer
) {

    fun play(url: String) {
        player.setMediaItem(
            MediaItem.fromUri(url)
        )
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    fun setVolume(volume: Float) {
        player.volume = volume
    }
}