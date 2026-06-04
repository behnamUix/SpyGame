package com.behnamuix.spy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class GameViewModel(private val roleManagerVm: RoleManagerViewModel) : ViewModel() {
    val questionList = mutableMapOf(
        "کمک اول" to "از طرف مقابل سوالات انحرافی بپرس (یه چیزی که به کلمه رمز ربط نداره)",
        "کمک دوم" to "کسی که خیلی ساکته رو بیار وسط بهش اتهام بزن",
    )

    private val _secondsLeft = MutableStateFlow(0)
    val secondsLeft: StateFlow<Int> = _secondsLeft.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    var isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _showDialog = MutableSharedFlow<Boolean>()
    var showDialog: SharedFlow<Boolean> = _showDialog

    var initialSeconds by mutableIntStateOf(0)

    init {
        viewModelScope.launch {
            roleManagerVm.baseTimeInMinutes.collect { minutes ->
                val totalSeconds = minutes * 60
                _secondsLeft.value = totalSeconds
                initialSeconds = totalSeconds  // ✅ اصلاح شد
            }
        }
    }

    fun startTimer() {
        _isRunning.value = true
        viewModelScope.launch {
            while (_secondsLeft.value > 0 && _isRunning.value) {
                delay(1000)
                _secondsLeft.value--
            }
            if (_secondsLeft.value == 0) {
                _showDialog.emit(true)
            }
            _isRunning.value = false
        }
    }

    fun stopTimer() {
        _isRunning.value = false
    }

    fun resumeTimer() {
        if (!_isRunning.value && _secondsLeft.value > 0) {
            _isRunning.value = true
            startTimer()  // ✅ دوباره تایمر رو شروع کن
        }
    }

    fun resetTimer() {
        stopTimer()
        _secondsLeft.value = initialSeconds
    }

    fun showTimerFormatedString(): String {
        val sec = _secondsLeft.value
        val min = sec / 60
        val remainingSec = sec % 60
        return String.format(Locale.US, "%02d:%02d", min, remainingSec)
    }

    fun calcProgress(): Float {
        if (initialSeconds == 0) return 0f
        return (_secondsLeft.value.toFloat() / initialSeconds.toFloat()) * 100
    }

    fun setTime(time: Int) {
        _secondsLeft.value = time * 60
        initialSeconds = time * 60  // ✅ حتماً این رو هم ست کن
    }
}