package com.behnamuix.spy.data.local.ds.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spy.data.local.ds.repository.DataStoreRepository
import com.behnamuix.spy.utils.setLog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataStoreViewModel(private val dataStoreRepo: DataStoreRepository) : ViewModel() {
    var userUse = dataStoreRepo.getUserUseState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = false
    )
    var agent = dataStoreRepo.getAgentCount()
    var spy = dataStoreRepo.getSpyCount()

    fun setUserUse(value: Boolean) {
        viewModelScope.launch {
            dataStoreRepo.setUserUseState(value)
        }
    }

    fun setAgent(value: Int) {
        viewModelScope.launch {
            setLog("$value added")
            dataStoreRepo.setAgentCount(value)
        }
    }

    fun setSpy(value: Int) {
        viewModelScope.launch {
            setLog("$value added")
            dataStoreRepo.setSpyCount(value)
        }
    }

}