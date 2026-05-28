package com.behnamuix.spy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spy.data.local.db.model.KeyWord
import com.behnamuix.spy.data.local.db.repository.KeywordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfigGameViewModel(private val keywordRepo: KeywordRepository) : ViewModel() {


    private val _wordList = MutableStateFlow(mutableListOf<KeyWord>())
    val wordList: StateFlow<MutableList<KeyWord>> = _wordList.asStateFlow()


    private val _expanded = MutableStateFlow<Boolean>(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()


    val agentCount = mutableIntStateOf(5)
    val spyCount = mutableIntStateOf(1)

    val wordExist = mutableStateOf(false)

    var showAddWordDialog = mutableStateOf(false)

    var userUse by mutableStateOf(false)


    var progress by mutableStateOf(true)


    //Room
    fun addWord(
        kyword: KeyWord,
    ) {
        viewModelScope.launch {
            keywordRepo.addKeywords(kyword)


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
            var list = keywordRepo.getKeywords()
            repeat(list.size) {
                _wordList.emit(list as MutableList<KeyWord>)
            }
        }


    }

    //===========================================================================


    fun incSpyCountPlayer() {
        if (spyCount.intValue < 3) {

            spyCount.intValue++
        }
    }

    fun incAgentCountPlayer() {

        if (agentCount.intValue < 10) {

            agentCount.intValue++
        }
        if (agentCount.intValue > 5) {
            incSpyCountPlayer()
        }

    }


    fun decAgentCountPlayer() {
        if (agentCount.intValue > 1) {

            agentCount.intValue--
        }
        if (agentCount.intValue < 5) {
            decSpyCountPlayer()
        }
    }

    fun decSpyCountPlayer() {
        if (spyCount.intValue > 1) {

            spyCount.intValue--
        }
    }



    fun configTime(): Int {
        var total = agentCount.intValue + spyCount.intValue
        //5-10
        //8-16
        return total * 2

    }

    fun reverseExpand() {
        _expanded.value = !_expanded.value
    }



}

