package com.behnamuix.spygame.core.media.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.core.media.controller.MusicController
import com.behnamuix.spygame.core.media.presentation.contract.MusicPlayerContract
import com.behnamuix.spygame.core.media.presentation.contract.PlayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicPlayerViewModel(private val controller: MusicController) : ViewModel() {
    private val _uiState = MutableStateFlow(MusicPlayerContract.UiState())
    val uiState: StateFlow<MusicPlayerContract.UiState> = _uiState.asStateFlow()

    fun onAction(action: MusicPlayerContract.UiAction) {
        when (action) {
            is MusicPlayerContract.UiAction.playRemote -> playBackgroundMusic(action.url)
            is MusicPlayerContract.UiAction.pause -> pauseMusic()
            is MusicPlayerContract.UiAction.stop -> stopMusic()
            is MusicPlayerContract.UiAction.seVolume -> setMusicVolume(action.volume)
        }
    }


    fun playBackgroundMusic(url: String) {
        viewModelScope.launch {
            controller.play(url)
            _uiState.update { it.copy(playState = PlayState.playing) }
        }
    }

    fun pauseMusic() {
        viewModelScope.launch {
            controller.pause()
            _uiState.update { it.copy(playState = PlayState.pause) }
        }
    }

    fun stopMusic() {
        viewModelScope.launch {
            controller.stop()
            _uiState.update { it.copy(playState = PlayState.stop) }
        }
    }

    fun setMusicVolume(volume: Float) {
        viewModelScope.launch {
            controller.setVolume(volume)

        }

    }


}
