package com.behnamuix.spy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
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
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _showDialog = MutableSharedFlow<Boolean>()
    val showDialog: SharedFlow<Boolean> = _showDialog

    var initialSeconds by mutableIntStateOf(0)

    // مدیریت کوروتین برای جلوگیری از ساخت موازی حلقه‌ها
    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel() // لغو تایمر قبلی در صورت وجود
        _isRunning.value = true

        timerJob = viewModelScope.launch {
            while (_secondsLeft.value > 0 && _isRunning.value) {
                delay(1000)
                _secondsLeft.value--
            }
            if (_secondsLeft.value == 0) {
                _showDialog.emit(true)
                _isRunning.value = false
            }
        }
    }

    fun stopTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun resumeTimer() {
        if (!_isRunning.value && _secondsLeft.value > 0) {
            startTimer()
        }
    }

    fun resetTimer() {
        stopTimer()
        _secondsLeft.value = initialSeconds
    }


    fun showTimerFormatedString(currentSeconds: Int): String {
        val min = currentSeconds / 60
        val remainingSec = currentSeconds % 60
        return String.format(Locale.US, "%02d:%02d", min, remainingSec)
    }


    fun calcProgress(currentSeconds: Int): Float {
        if (initialSeconds == 0) return 0f
        return currentSeconds.toFloat() / initialSeconds.toFloat()
    }

    fun setTime(time: Int) {
        val totalSeconds = time * 60
        _secondsLeft.value = totalSeconds
        initialSeconds = totalSeconds
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}