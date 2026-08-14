package com.behnamuix.spygame.core.media.presentation.contract

object MusicPlayerContract {
    data class UiState(
        val playState: PlayState = PlayState.pause
    )

    sealed interface UiAction {
        data object stop : UiAction
        data object pause : UiAction
        data class playRemote(val url: String) : UiAction
        data class seVolume(val volume: Float) : UiAction
    }
}

enum class PlayState {
    playing,
    pause,
    stop

}