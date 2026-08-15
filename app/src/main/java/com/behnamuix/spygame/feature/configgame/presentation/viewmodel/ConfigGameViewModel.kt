package com.behnamuix.spygame.feature.configgame.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.core.media.controller.MusicController
import com.behnamuix.spygame.data.local.db.model.KeyWord
import com.behnamuix.spygame.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.feature.configgame.domain.usecase.ConfigGameUseCase
import com.behnamuix.spygame.feature.configgame.presentation.contract.ConfigGameContract
import com.behnamuix.spygame.utils.setLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfigGameViewModel(
    private val keywordRepo: KeywordRepository,
    private val controller: MusicController,
    private val useCase: ConfigGameUseCase
) : ViewModel() {
    val listTrack = listOf(
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/01%20A%20Crucial%20Moment.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/04%20Political%20Tactics.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/02%20Great%20Women.mp3"
    )

    private val _configGameState = MutableStateFlow(ConfigGameContract.ConfigGameState())
    val configGameState: StateFlow<ConfigGameContract.ConfigGameState> = _configGameState.asStateFlow()



    fun onAction(action: ConfigGameContract.ConfigGameAction) {
        when (action) {
            is ConfigGameContract.ConfigGameAction.SetEnabled -> setEnabled(action.enabled)

            is ConfigGameContract.ConfigGameAction.AddWord -> addWord(action.word)
            is ConfigGameContract.ConfigGameAction.DeleteWord -> deleteWord(action.id)
            ConfigGameContract.ConfigGameAction.GetWords -> getWords()
            is ConfigGameContract.ConfigGameAction.CheckWordExist -> checkWordExist(action.word)

            ConfigGameContract.ConfigGameAction.IncreaseAgentCount -> incAgentCountPlayer()
            ConfigGameContract.ConfigGameAction.DecreaseAgentCount -> decAgentCountPlayer()
            ConfigGameContract.ConfigGameAction.IncreaseSpyCount -> incSpyCountPlayer()
            ConfigGameContract.ConfigGameAction.DecreaseSpyCount -> decSpyCountPlayer()

            ConfigGameContract.ConfigGameAction.ReverseExpand -> reverseExpand()

            is ConfigGameContract.ConfigGameAction.Initialize -> init(
                agent = action.agentCount,
                spy = action.spyCount
            )

            ConfigGameContract.ConfigGameAction.PlayMusic -> play()
            ConfigGameContract.ConfigGameAction.PauseMusic -> pause()
            ConfigGameContract.ConfigGameAction.SetMusicVolume -> setVolume()

//            is ConfigGameContract.ConfigGameAction.SetProgress -> progress = action.value
        }
    }

    fun setEnabled(value: Boolean) {
        _configGameState.update {
            it.copy(
                enabled = value
            )
        }
    }

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
                _spyCount.value = useCase.incSpyCountPlayer()
                _spyCode.value = useCase.getSpyCode()

            }
        }
    }

    fun incAgentCountPlayer() {

        if (_agentCount.value < 10) {

            viewModelScope.launch {
                _agentCount.value = useCase.incAgentCountPlayer()
                _agentCode.value = useCase.getAgentCode()

            }
        }
        if (_agentCount.value > 5) {
            incSpyCountPlayer()
        }

    }


    fun decAgentCountPlayer() {
        if (_agentCount.value > 2) {

            viewModelScope.launch {
                _agentCount.value = useCase.decAgentCountPlayer()
                _agentCode.value = useCase.getAgentCode()


            }
        }
        if (_agentCount.value < 5) {
            decSpyCountPlayer()
        }
    }

    fun decSpyCountPlayer() {
        if (_spyCount.value > 1) {
            _spyCount.value = useCase.decSpyCountPlayer()
            _spyCode.value = useCase.getSpyCode()

        }
    }

    fun reverseExpand() {
        viewModelScope.launch {
            delay(200)
            _expanded.value = !_expanded.value
        }

    }

    fun userUseOperation(dsVm: DataStoreViewModel, setCheck: (Boolean) -> Unit) {
        viewModelScope.launch {
            dsVm.userUse.collect {
                setCheck(it)
                if (it) {
                    reverseExpand()

                }
            }
        }

    }

    fun init(agent: Int, spy: Int) {
        _agentCount.value = agent
        _spyCount.value = spy
    }

    fun checkWordExist(word: String) {
        viewModelScope.launch {
            _wordList.value.forEach {
                setLog(it.word)
                if (it.word == word) {
                    _wordExist.emit(true)
                } else {
                    _wordExist.emit(false)
                }
            }
        }

    }


    //MediaController
    fun play() {
        try {
            // mediaVm.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun pause() {
        // mediaVm.pause()

    }

    fun setVolume() {
        // mediaVm.volumeHigh()
    }


}



