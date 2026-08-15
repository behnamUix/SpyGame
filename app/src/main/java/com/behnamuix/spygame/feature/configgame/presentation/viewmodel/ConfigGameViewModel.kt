package com.behnamuix.spygame.feature.configgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.core.media.controller.MusicController
import com.behnamuix.spygame.data.local.db.model.KeyWord
import com.behnamuix.spygame.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.feature.configgame.domain.usecase.ConfigGameUseCase
import com.behnamuix.spygame.feature.configgame.presentation.contract.ConfigGameContract
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
    private val _configGameState =
        MutableStateFlow(ConfigGameContract.ConfigGameState())

    val configGameState: StateFlow<ConfigGameContract.ConfigGameState> =
        _configGameState.asStateFlow()

    fun onAction(action: ConfigGameContract.ConfigGameAction) {
        when (action) {
            is ConfigGameContract.ConfigGameAction.SetEnabled ->
                setEnabled(action.enabled)

            is ConfigGameContract.ConfigGameAction.AddWord ->
                addWord(action.word)

            is ConfigGameContract.ConfigGameAction.DeleteWord ->
                deleteWord(action.id)

            ConfigGameContract.ConfigGameAction.GetWords ->
                getWords()

            is ConfigGameContract.ConfigGameAction.CheckWordExist ->
                checkWordExist(action.word)

            ConfigGameContract.ConfigGameAction.IncreaseAgentCount ->
                incAgentCountPlayer()

            ConfigGameContract.ConfigGameAction.DecreaseAgentCount ->
                decAgentCountPlayer()

            ConfigGameContract.ConfigGameAction.IncreaseSpyCount ->
                incSpyCountPlayer()

            ConfigGameContract.ConfigGameAction.DecreaseSpyCount ->
                decSpyCountPlayer()

            ConfigGameContract.ConfigGameAction.ReverseExpand ->
                reverseExpand()

            is ConfigGameContract.ConfigGameAction.Initialize ->
                init(action.agentCount, action.spyCount)

            ConfigGameContract.ConfigGameAction.PlayMusic ->
                play()

            ConfigGameContract.ConfigGameAction.PauseMusic ->
                pause()

            ConfigGameContract.ConfigGameAction.SetMusicVolume ->
                setVolume()

            ConfigGameContract.ConfigGameAction.ShowAddWordDialog ->
                updateState { copy(showAddWordDialog = true) }

            ConfigGameContract.ConfigGameAction.HideAddWordDialog ->
                updateState { copy(showAddWordDialog = false) }

            is ConfigGameContract.ConfigGameAction.SetProgress ->
                updateState { copy(progress = action.value) }
        }
    }

    private fun updateState(
        block: ConfigGameContract.ConfigGameState.() ->
        ConfigGameContract.ConfigGameState
    ) {
        _configGameState.update(block)
    }

    fun setEnabled(value: Boolean) {
        updateState { copy(enabled = value) }
    }

    fun addWord(keyword: KeyWord) {
        viewModelScope.launch {
            keywordRepo.addKeywords(keyword)
            getWords()
        }
    }

    fun deleteWord(id: Int) {
        viewModelScope.launch {
            keywordRepo.deleteKeywords(KeyWord(id = id, word = ""))
            getWords()
        }
    }

    fun getWords() {
        viewModelScope.launch {
            val words = keywordRepo.getKeywords()
            updateState { copy(wordList = words) }
        }
    }

    fun checkDb(): Boolean {
        return configGameState.value.wordList.isEmpty()
    }

    fun incAgentCountPlayer() {
        if (configGameState.value.agentCount >= 10) return

        val count = useCase.incAgentCountPlayer()
        val code = useCase.getAgentCode()

        updateState {
            copy(
                agentCount = count,
                agentCode = code
            )
        }

        if (count > 5) {
            incSpyCountPlayer()
        }
    }

    fun decAgentCountPlayer() {
        if (configGameState.value.agentCount <= 2) return

        val count = useCase.decAgentCountPlayer()
        val code = useCase.getAgentCode()

        updateState {
            copy(
                agentCount = count,
                agentCode = code
            )
        }

        if (count < 5) {
            decSpyCountPlayer()
        }
    }

    fun incSpyCountPlayer() {
        if (configGameState.value.spyCount >= 3) return

        val count = useCase.incSpyCountPlayer()
        val code = useCase.getSpyCode()

        updateState {
            copy(
                spyCount = count,
                spyCode = code
            )
        }
    }

    fun decSpyCountPlayer() {
        if (configGameState.value.spyCount <= 1) return

        val count = useCase.decSpyCountPlayer()
        val code = useCase.getSpyCode()

        updateState {
            copy(
                spyCount = count,
                spyCode = code
            )
        }
    }

    fun reverseExpand() {
        viewModelScope.launch {
            delay(200)
            updateState { copy(expanded = !expanded) }
        }
    }

    fun init(agent: Int, spy: Int) {
        updateState {
            copy(
                agentCount = agent,
                spyCount = spy,
                agentCode = useCase.getAgentCode(),
                spyCode = useCase.getSpyCode()
            )
        }
    }

    fun checkWordExist(word: String) {
        val exists = configGameState.value.wordList.any {
            it.word.equals(word.trim(), ignoreCase = true)
        }

        updateState { copy(wordExist = exists) }
    }

    fun userUseOperation(
        dsVm: DataStoreViewModel,
        setCheck: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            dsVm.userUse.collect { isUsed ->
                setCheck(isUsed)

                if (isUsed) {
                    reverseExpand()
                }
            }
        }
    }

    fun play() {
         controller.play(listTrack.random())
    }

    fun pause() {
         controller.pause()
    }

    fun setVolume() {
         controller.setVolume(1f)
    }
}