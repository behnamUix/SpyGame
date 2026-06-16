package com.behnamuix.spygame.ui.navigation.screens.configGame.components.otp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.behnamuix.spygame.data.remote.authentication.viewModel.ApiViewModel

@SuppressLint("NewApi")
@Composable
fun LoginWithOtp(
    apiVm: ApiViewModel,
    context: Context,
    setPage: (String) -> Unit,
    setShow: (Boolean) -> Unit
) {
    val phone = apiVm.phone.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)

    ) {

        Text(
            "ثبت نام / ورود",
            fontWeight = FontWeight.Bold
        )
        Text(
            "شماره تلفن خودت رو وارد کن تا کد برات ارسال بشه",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),

            value = phone,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { apiVm.setPhone(it) },
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton (
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if(apiVm.checkPhoneNumber()){
                        //apiVm.sendVerificationCode()
                        setPage("otp")
                    }else{
                        Toast.makeText(
                            context,
                            "شماره تلفن نمیتونه خالی باشه",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }) {
                Text(
                    "ارسال کد 4 رقمی",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    setShow(false)


                }) {
                Text(
                    "بیخیال",
                    color = Color.White.copy(0.5f),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

    }
}