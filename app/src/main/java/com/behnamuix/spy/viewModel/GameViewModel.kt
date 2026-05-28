package com.behnamuix.retrofittest.SpyGame.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class GameViewModel(ctx: Context) : ViewModel() {

    val questionList = mutableMapOf(
        "کمک اول" to "از طرف مقابل سوالات انحرافی بپرس (یه چیزی که به کلمه رمز ربط نداره)",
        "کمک دوم" to "از جنس اون چیز میتونی تو سوالت استفاده کنی",
        "کمک سوم" to "کسی که خیلی ساکته رو بیار وسط بهش اتهام بزن",
        "نکته" to "هیچکس جاسوس صد درصدی نیست مگر اینکه واقعان اشتباه جواب بده",
    )

    // زمان پایه به دقیقه
    private val _baseTimeInMinutes = MutableStateFlow(1)
    val baseTimeInMinutes: StateFlow<Int> = _baseTimeInMinutes.asStateFlow()

    // زمان باقی مانده به ثانیه
    private val _secondsLeft =
        MutableStateFlow(0) // مقدار اولیه را بر اساس baseTimeInMinutes تنظیم کنید
    val secondsLeft: StateFlow<Int> = _secondsLeft.asStateFlow()

    // وضعیت اجرای تایمر
    private val _isRunning = MutableStateFlow(false)
    var isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    var initialSeconds by mutableIntStateOf(0) // برای محاسبه progress

    init {
        // مقدار اولیه ثانیه ها را بر اساس دقیقه تنظیم کن
        viewModelScope.launch {
            _baseTimeInMinutes.collect { minutes ->
                val totalSeconds = minutes * 60
                _secondsLeft.value = totalSeconds
                initialSeconds = totalSeconds // برای محاسبه progress
            }
        }
    }

    fun startTimer() {
        _isRunning.value = true
        viewModelScope.launch {
            while (_isRunning.value) {
                delay(1000)
                _secondsLeft.value--
            }
            if (_secondsLeft.value == 0) {
                _isRunning.value = false
                // اینجا می توانید یک event برای اتمام زمان بفرستید
                // مثلا: _timerFinishedEvent.emit(Unit)
            }
        }
    }

    fun stopTimer() {
        _isRunning.value = false
    }

    fun resetTimer() {
//        stopTimer()
        _secondsLeft.value = initialSeconds
    }

    fun setTime(minutes: Int) {
        val clampedMinutes = minutes.coerceIn(5, 15) // محدود کردن بین 5 تا 15 دقیقه
        _baseTimeInMinutes.value = clampedMinutes
        // این باعث میشه _secondsLeft هم آپدیت بشه چون از collect استفاده کردیم
    }

    fun incTime() {
        setTime((_baseTimeInMinutes.value + 5).coerceAtMost(15))
    }

    fun decTime() {
        setTime((_baseTimeInMinutes.value - 5).coerceAtMost(5)) // حداقل 5 دقیقه
    }

    fun showTimerFormatedString(): String {
        val sec = secondsLeft.value
        val min = sec / 60
        val remainingSec = sec % 60
        return String.format(Locale.US, "%02d:%02d", min, remainingSec)
    }

    fun calculateProgressValue(): Float {
        return if (initialSeconds > 0) {
            secondsLeft.value.toFloat() / initialSeconds.toFloat()
        } else {
            0f
        }
    }
}

