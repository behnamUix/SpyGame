package com.behnamuix.spygame.ui.navigation.screens.configGame.components.otp

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.behnamuix.spygame.data.remote.authentication.viewModel.ApiViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBottomSheetComp(show: MutableState<Boolean>, apiVm: ApiViewModel = koinViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    var page by remember { mutableStateOf("login") }
    ModalBottomSheet(
        onDismissRequest = { show.value = false },
        sheetState = sheetState
    ) {
        LaunchedEffect(Unit) {
            apiVm.otpState.collect {
                if (it) {
                    Toast.makeText(context, "کد ارسال شد", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "خطا در ارسال", Toast.LENGTH_SHORT).show()

                }
            }
        }
// Sheet content
        when (page) {
            "login" -> {
                LoginWithOtp(apiVm, context, setPage = { page = it }) { show.value = it }
            }

            "otp" -> {
                OtpVerification(setPage={page=it}) {
                    show.value = it
                }
            }
        }

    }
}

