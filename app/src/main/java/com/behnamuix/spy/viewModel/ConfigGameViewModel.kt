package com.behnamuix.retrofittest.SpyGame.viewModel

import SpyGameSimulator.model.Player
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.retrofittest.SpyGame.db.DatabaseProvider
import com.behnamuix.retrofittest.SpyGame.db.KeyWordEntity
import com.behnamuix.retrofittest.SpyGame.view.ui.createUnpredictableShuffle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfigGameViewModel(ctx: Context) : ViewModel() {
    private val _playerList = MutableStateFlow(mutableListOf<Player>())
    val playerList: StateFlow<MutableList<Player>> = _playerList.asStateFlow()

    private val _wordList = MutableStateFlow(mutableListOf<KeyWordEntity>())
    val wordList: StateFlow<MutableList<KeyWordEntity>> = _wordList.asStateFlow()


    val agentCount = mutableIntStateOf(5)
    val spyCount = mutableIntStateOf(1)


    val wordExist = mutableStateOf(false)
    val dao = DatabaseProvider.getKeywordDao(ctx)






    //Room
    fun addWord(
        word: String,
    ) {
        viewModelScope.launch {
            dao.insert(KeyWordEntity(word = word))
            getWords()


        }
    }

    fun checkDb(): Boolean {
        return _wordList.value.isEmpty()
    }

    fun deleteWord(id: Int) {
        viewModelScope.launch {
            dao.delete(KeyWordEntity(id = id, word = ""))
            getWords()


        }
    }

    fun getWords() {

        viewModelScope.launch {
            var list = dao.getAll()
            repeat(list.size) {
                _wordList.emit(list)
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

    fun configRole() {
        var list = mutableListOf<Player>()

        for (i in 1..agentCount.intValue) {
            list.add(Player(i, "تو الان یه مامور هستی"))

        }
        for (i in 1..spyCount.intValue) {
            list.add(Player(i, "تو یه جاسوسی"))
        }
        viewModelScope.launch {

            _playerList.emit(createUnpredictableShuffle(list) as MutableList<Player>)
        }
        Log.d("LOG_ROLE", "${list.size}")


    }

    fun configTime(): Int {
        var total = agentCount.intValue + spyCount.intValue
        //5-10
        //8-16
        return total * 2

    }




}

