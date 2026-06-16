package com.behnamuix.spygame.data.remote.authentication.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.BuildConfig
import com.behnamuix.spygame.data.local.ds.repository.DataStoreRepository
import com.behnamuix.spygame.data.remote.authentication.model.ReqApi
import com.behnamuix.spygame.data.remote.authentication.repository.ApiRepository
import com.behnamuix.spygame.utils.setLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.security.SecureRandom

class ApiViewModel(private val repo: ApiRepository, private val dsRepo: DataStoreRepository) :
    ViewModel() {
    val phone = mutableStateOf("")
    private val _otpState = MutableSharedFlow<Boolean>()
    val otpState: SharedFlow<Boolean> = _otpState

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendVerificationCode() {
        val otp = SecureRandom().nextInt(9000) + 1000
        viewModelScope.launch {
            try {
                val reqApi = ReqApi(
                    username = BuildConfig.API_USERNAME,
                    password = BuildConfig.API_PASSWORD,
                    to = phone.value,
                    from = "50002710050718",
                    text = otp.toString() + "لغو 11"
                )
                val resp = repo.sendSms(reqApi)
                if (resp?.StrRetStatus.equals("Ok")) {
                    dsRepo.setOtpCode(otp.toString())
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

    fun setPhone(str: String) {
        phone.value = str
    }

    fun checkPhoneNumber(): Boolean {
        if (!phone.value.isEmpty()) {
            if (phone.value.length <= 11) {
                return true
            }
        }
        return false
    }
}