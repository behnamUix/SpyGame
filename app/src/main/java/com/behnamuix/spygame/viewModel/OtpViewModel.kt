package com.behnamuix.spygame.viewModel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.ui.navigation.Screens
import com.behnamuix.spygame.utils.setLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.SecureRandom

class OtpViewModel : ViewModel() {
    val initialTime = 60

    var timerValue by mutableStateOf(initialTime)

    var isTimerRunning by mutableStateOf(true)
    var phone = mutableStateOf("")
    var _otp = MutableStateFlow<String>("")
    var otp: StateFlow<String> = _otp.asStateFlow()

    val _toastSideEffect = MutableSharedFlow<String>()
    val toastSideEffect: SharedFlow<String> = _toastSideEffect

    private val _userCode = MutableStateFlow("")


    var resendButtonEnabled by mutableStateOf(false)

    fun setUserCode(value: String) {
        _userCode.value = value
    }

    fun setIsTimerRunning(value: Boolean) {
        isTimerRunning = value
    }

    fun setTimer(value: Int) {
        timerValue = value
    }

    fun setPhone(value: String) {
        phone.value = value
    }
    fun setOtp(value: String) {
        _otp.value = value
    }

    fun checkPhoneNumber(): Boolean {
        if (!phone.value.isEmpty()) {
            if (phone.value.length <= 11) {
                return true
            }
        }
        return false
    }

    fun getOtp(): String {
        return (SecureRandom().nextInt(9000) + 1000).toString()

    }

    fun startOtpTimer() {
        viewModelScope.launch {
            if (isTimerRunning && timerValue > 0) {
                resendButtonEnabled = false
                delay(1000L)
                timerValue -= 1
            } else if (timerValue == 0) {
                isTimerRunning = false
                resendButtonEnabled = true
            }
        }
    }

    fun checkAuthentication(code: String, ok: () -> Unit) {
        setLog(code + "/" + _userCode.value)
        viewModelScope.launch {
            val isOtpValid = _userCode.value.length == 4
            if (!isOtpValid) {
                _toastSideEffect.emit("notCompleted")

            }

            if (code == _userCode.value) {
                _toastSideEffect.emit("welcome")
                ok()

            } else {
                _toastSideEffect.emit("notWelcome")
            }
        }

    }
}