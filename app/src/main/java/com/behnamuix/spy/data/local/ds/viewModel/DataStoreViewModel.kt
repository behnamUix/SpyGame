package com.behnamuix.spy.data.local.ds.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spy.data.local.ds.repository.DataStoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataStoreViewModel(private val dataStoreRepo: DataStoreRepository) : ViewModel() {
    val userUse = dataStoreRepo.getUserUseState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = false
    )

    fun setUserUse(value: Boolean) {
        viewModelScope.launch {
            dataStoreRepo.setUserUseState(value)
        }
    }

}