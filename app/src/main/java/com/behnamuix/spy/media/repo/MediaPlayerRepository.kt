package com.behnamuix.spy.media.repo

import android.content.Context
import android.media.MediaPlayer
import com.behnamuix.spy.media.config.getMediaPlayer
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel

class MediaPlayerRepository(private var mp: MediaPlayer) {


    fun play() {
        if (mp.isPlaying) return
        try {
            mp.setOnPreparedListener { player ->
                setVolume(1f, 1f)
                player.start()
            }
            mp.start()
        } catch (e: IllegalStateException) {

        }





    }


    fun pause() {
        if (mp.isPlaying) {
            mp.pause()
        }

    }

    fun stop() {
        try {
            mp.stop()
            // برای استفاده مجدد بعد از stop، باید مجدد prepareAsync شود
            mp.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun release() {
        mp.release()

    }

    fun isPlaying(): Boolean {
        if (mp.isPlaying) {
            return true
        } else {
            return false
        }
    }

    fun setVolume(L: Float, R: Float) {
        mp.setVolume(L, R)
    }

}

