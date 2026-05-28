package com.behnamuix.spy.media.repo

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerRepository(private val mp: MediaPlayer?) {

    fun play() {
        if (mp != null) {
            if (!mp.isPlaying) {
                mp.start()
            }
        }
    }

    fun pause() {
        if (mp != null) {
            if (mp.isPlaying) {
                mp.pause()
            }
        }
    }

    fun stop() {
        if (mp != null) {
            if (mp.isPlaying) {
                mp.stop()
                mp.seekTo(0)
            }
        }
    }

    fun release() {
        mp?.release()

    }

    fun isPlaying(): Boolean? {
        return mp?.isPlaying
    }

    fun setVolume(L: Float, R: Float) {
        mp?.setVolume(L, R)
    }

}