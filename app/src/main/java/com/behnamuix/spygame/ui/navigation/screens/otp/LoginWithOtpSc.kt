package com.behnamuix.spygame.ui.navigation.screens.otp

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.data.remote.authentication.viewModel.ApiViewModel
import com.behnamuix.spygame.ui.navigation.Screens
import com.behnamuix.spygame.utils.setLog
import com.behnamuix.spygame.viewModel.OtpViewModel
import io.ktor.util.collections.getValue
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithOtpSc(
    apiVm: ApiViewModel = koinViewModel(),
    otpVm: OtpViewModel = koinViewModel(),
    dsVm: DataStoreViewModel = koinViewModel(),
    navController: NavController,
) {
    val phone = otpVm.phone.value
    val unitOtp by otpVm.otp.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        otpVm.setOtp(otpVm.getOtp())
        apiVm.otpState.collect {
            if (it) {
                Toast.makeText(context, "کد تایید ارسال شد", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        "ورود یا ثبت‌ نام",
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "لطفاً شماره همراه خود را جهت دریافت کد تایید وارد کنید",
                        color = Color.Black.copy(0.8f),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,

                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .testTag("phone_number")
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    value = phone,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { if (it.length <= 11) otpVm.setPhone(it) },
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        if (otpVm.checkPhoneNumber()) {
                            setLog("Login / $phone / $unitOtp")
                            apiVm.sendVerificationCode(otp = unitOtp, phone = phone)
                            navController.navigate(
                                Screens.OtpVerification.createRoute(
                                    unitOtp,
                                    phone = phone
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "لطفاً شماره همراه معتبر وارد کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text(
                        "دریافت کد تایید",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                    )
                }
                TextButton(
                    shape = RoundedCornerShape(8.dp),
                    onClick = { dsVm.setLoggedInState(false) }
                ) {
                    Text(
                        "ورود به عنوان مهمان",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )
                }

            }
        }
    }
}

