package com.behnamuix.spygame.core.media.di

import androidx.media3.exoplayer.ExoPlayer
import com.behnamuix.spygame.core.media.controller.MusicController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mediaModule = module {

    single<ExoPlayer> {
        ExoPlayer.Builder(androidContext()).build()
    }

    single {
        MusicController(
            player = get()
        )
    }


}