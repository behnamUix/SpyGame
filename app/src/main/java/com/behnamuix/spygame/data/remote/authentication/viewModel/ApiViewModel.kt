package com.behnamuix.spygame.data.remote.authentication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.BuildConfig
import com.behnamuix.spygame.data.remote.authentication.model.ReqApi
import com.behnamuix.spygame.data.remote.authentication.repository.ApiRepository
import com.behnamuix.spygame.utils.setLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ApiViewModel(private val repo: ApiRepository) :
    ViewModel() {
    private val _otpState = MutableSharedFlow<Boolean>()
    val otpState: SharedFlow<Boolean> = _otpState

    fun sendVerificationCode(phone: String, otp: String) {
        setLog("send:$otp")
        viewModelScope.launch {
            try {
                val reqApi = ReqApi(
                    username = BuildConfig.API_USERNAME,
                    password = BuildConfig.API_PASSWORD,
                    to = phone,
                    from = "50002710050718",
                    text = "خوش آمدید\nکد تایید شما برای بازی جاسوس:\n$otp\nلغو 11"
                )
                val resp = repo.sendSms(reqApi)
                if (resp?.StrRetStatus.equals("Ok")) {
                    _otpState.emit(true)
                } else {
                    _otpState.emit(false)

                }

            } catch (e: Exception) {
                setLog(e.message.toString())
                _otpState.emit(false)
            }
        }
    }


}