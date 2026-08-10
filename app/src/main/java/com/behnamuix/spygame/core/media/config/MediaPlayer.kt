package com.behnamuix.spygame.core.media.config

import android.media.MediaPlayer
import com.behnamuix.spygame.utils.getRandom

fun getMediaPlayer(): MediaPlayer {
    val listTrack = listOf(
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/01%20A%20Crucial%20Moment.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/04%20Political%20Tactics.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/02%20Great%20Women.mp3"
    )
    val media = MediaPlayer().apply {
        isLooping = true
        setDataSource(listTrack[getRandom(listTrack.size)])
    }
    //hatman bayad ghabl az start amade sazi shavad
    media.prepareAsync()

    return media
}