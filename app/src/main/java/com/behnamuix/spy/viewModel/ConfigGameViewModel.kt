package com.behnamuix.spy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spy.data.local.db.model.KeyWord
import com.behnamuix.spy.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfigGameViewModel(
    private val keywordRepo: KeywordRepository,
    private val mediaVm: MediaPlayerViewModel
) : ViewModel() {


    private val _wordList = MutableStateFlow<List<KeyWord>>(emptyList())
    val wordList: StateFlow<List<KeyWord>> = _wordList.asStateFlow()


    private val _expanded = MutableStateFlow<Boolean>(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()


    private val _agentCount = MutableStateFlow<Int>(4)
    val agentCount: StateFlow<Int> = _agentCount.asStateFlow()

    private val _spyCount = MutableStateFlow<Int>(1)
    val spyCount: StateFlow<Int> = _spyCount.asStateFlow()

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.PLAY)
    val mediaState: StateFlow<MediaState> = _mediaState.asStateFlow()


    private val _userUse = MutableStateFlow(false)
    val userUse: StateFlow<Boolean> = _userUse.asStateFlow()


    val wordExist = mutableStateOf(false)

    var showAddWordDialog = mutableStateOf(false)


    var progress by mutableStateOf(true)


    //Room
    fun addWord(
        kyword: KeyWord,
    ) {
        viewModelScope.launch {
            keywordRepo.addKeywords(kyword)
            getWords()


        }
    }

    fun checkDb(): Boolean {
        return _wordList.value.isEmpty()
    }

    fun deleteWord(id: Int) {
        viewModelScope.launch {
            keywordRepo.deleteKeywords(KeyWord(id = id, word = ""))
            getWords()


        }
    }

    fun getWords() {
        viewModelScope.launch {
            _wordList.emit(keywordRepo.getKeywords())
        }


    }

    //===========================================================================


    fun incSpyCountPlayer() {
        if (_spyCount.value < 3) {
            viewModelScope.launch {
                +_spyCount.value++
            }
        }
    }

    fun incAgentCountPlayer() {

        if (_agentCount.value < 10) {

            viewModelScope.launch {
                _agentCount.value++
            }
        }
        if (_agentCount.value > 5) {
            incSpyCountPlayer()
        }

    }


    fun decAgentCountPlayer() {
        if (_agentCount.value > 1) {

            viewModelScope.launch {
                _agentCount.value--
            }
        }
        if (_agentCount.value < 5) {
            decSpyCountPlayer()
        }
    }

    fun decSpyCountPlayer() {
        if (_spyCount.value > 1) {
            _spyCount.value--
        }
    }


    fun configTime(): Int {
        val total = agentCount.value + spyCount.value
        //5-10
        //8-16
        return total * 2

    }

    fun reverseExpand() {
        _expanded.value = !_expanded.value
    }


    //MediaController
    fun play() {
        mediaVm.play()
        _mediaState.value = MediaState.PLAY
    }

    fun pause() {
        mediaVm.pause()
        _mediaState.value = MediaState.PAUSE
    }

    fun setVolume() {
        mediaVm.volumeHigh()
    }

    fun toggleUserUse(value: Boolean) {
        _userUse.value = value
    }


}

enum class MediaState {
    PLAY,
    PAUSE,
    STOP
}

