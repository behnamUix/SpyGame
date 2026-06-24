package com.behnamuix.spygame.ui.navigation.screens.otp

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.data.remote.authentication.viewModel.ApiViewModel
import com.behnamuix.spygame.ui.navigation.Screens
import com.behnamuix.spygame.utils.setLog
import com.behnamuix.spygame.viewModel.OtpViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun OtpVerificationSc(
    code: String,
    phone: String,
    navController: NavController,
    dsVm: DataStoreViewModel = koinViewModel(),
    apiVm: ApiViewModel = koinViewModel(),
    otpVm: OtpViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val timerValue = otpVm.timerValue
    val initialTime = otpVm.initialTime
    LaunchedEffect(Unit) {
        setLog(code)
        otpVm.toastSideEffect.collect {
            when (it) {
                "notCompleted" -> {
                    Toast.makeText(context, "کد کامل وارد نشده", Toast.LENGTH_SHORT).show()

                }

                "welcome" -> {
                    Toast.makeText(context, "با موفقیت وارد شدید", Toast.LENGTH_SHORT).show()
                }

                "notWelcome" -> {
                    setLog(otpVm.otp.value)
                    Toast.makeText(context, "❌ کد اشتباه است!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(key1 = otpVm.isTimerRunning, key2 = otpVm.timerValue) {
        otpVm.startOtpTimer()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier
            .animateContentSize()
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        "کد تأیید",
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "کد تأیید ۴ رقمی را که برای شما پیامک شده است، وارد کنید.",
                        color = Color.Black.copy(0.8f),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,

                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(16.dp))

                SquareOtpInputComp(4) { otpVm.setUserCode( it) }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (timerValue > 0) {
                        Text(
                            text = String.format(
                                " مانده تا ارسال مجدد %02d:%02d ",
                                timerValue / 60,
                                timerValue % 60
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    } else {
                        TextButton(

                            onClick = {
                                apiVm.sendVerificationCode(phone, code)
                                otpVm.setTimer(initialTime)
                                otpVm.setIsTimerRunning(true)
                            }
                        ) {
                            Text(
                                "ارسال مجدد کد",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        otpVm.checkAuthentication(code) {
                            dsVm.setLoggedInState(true)
                            navController.navigate(Screens.ConfigGame.route)
                        }
                    }
                ) {
                    Text(
                        "تأیید و ادامه",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                    )
                }
                TextButton(
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        dsVm.setLoggedInState(false)
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        "بازگشت",
                        color = Color.Black.copy(0.5f),
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


@Composable
fun SquareOtpInputComp(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit = {},
) {
    var field1 by remember { mutableStateOf("") }
    var field2 by remember { mutableStateOf("") }
    var field3 by remember { mutableStateOf("") }
    var field4 by remember { mutableStateOf("") }

    val focusRequesters = remember {
        List(otpLength) { FocusRequester() }
    }

    fun checkComplete() {
        val otp = field1 + field2 + field3 + field4

        if (otp.length == otpLength && otp.all(Char::isDigit)) {
            onOtpComplete(otp)
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        // Field 1
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(width = 56.dp, height = 64.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(

                value = field1,
                onValueChange = { value ->
                    val digit = value.filter(Char::isDigit).take(1)

                    field1 = digit

                    if (digit.isNotEmpty()) {
                        focusRequesters[1].requestFocus()
                    }

                    checkComplete()
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequesters[0])
                    .testTag("otp_0")
            )
        }

        // Field 2
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(width = 56.dp, height = 64.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = field2,
                onValueChange = { value ->
                    val digit = value.filter(Char::isDigit).take(1)

                    field2 = digit

                    if (digit.isNotEmpty()) {
                        focusRequesters[2].requestFocus()
                    }

                    checkComplete()
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)

                    .fillMaxSize()
                    .focusRequester(focusRequesters[1])
                    .testTag("otp_1")
            )
        }

        // Field 3
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(width = 56.dp, height = 64.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = field3,
                onValueChange = { value ->
                    val digit = value.filter(Char::isDigit).take(1)

                    field3 = digit

                    if (digit.isNotEmpty()) {
                        focusRequesters[3].requestFocus()
                    }

                    checkComplete()
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)

                    .fillMaxSize()
                    .focusRequester(focusRequesters[2])
                    .testTag("otp_2")
            )
        }

        // Field 4
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(width = 56.dp, height = 64.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = field4,
                onValueChange = { value ->
                    val digit = value.filter(Char::isDigit).take(1)

                    field4 = digit

                    checkComplete()
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)

                    .fillMaxSize()
                    .focusRequester(focusRequesters[3])
                    .testTag("otp_3")
            )
        }
    }
}