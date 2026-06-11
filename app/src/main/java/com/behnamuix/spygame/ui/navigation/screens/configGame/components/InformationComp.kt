package com.behnamuix.spygame.ui.navigation.screens.configGame.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.behnamuix.spygame.authentication.viewModel.GoogleAuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun InformationComp(authVm: GoogleAuthViewModel = koinViewModel()) {

    val ctx = LocalContext.current
    val userProfile = authVm.currentUserProfile.collectAsState()

    val versionName = try {
        val packageInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
        packageInfo.versionName
    } catch (e: Exception) { "1.0.0" }

    val infiniteTrans = rememberInfiniteTransition()
    val translateY by infiniteTrans.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        Modifier


            .alpha(0.6f)
            .fillMaxWidth()
            .offset(x = 0.dp, y = translateY.dp)
            .padding(16.dp), contentAlignment = Alignment.TopCenter
    ) {
        OutlinedCard(
            modifier =
                Modifier
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("طراحی و توسعه : بهنام محجوب")
                Text("نسخه $versionName")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(

                        border = BorderStroke(
                            1.dp, color = Color(0xFFFFFF00),
                        ),
                        shape = RoundedCornerShape(8.dp), onClick = {
                            val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = "https://t.me/behnamUix".toUri()
                                `package` = "org.telegram.messenger"
                            }
                            try {
                                ctx.startActivity(telegramIntent)
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(
                                    ctx,
                                    "تلگرام نصب نیست!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            Text(
                                text = "پشتیبانی",
                                color = Color(0xFFFFFF00),
                                style = MaterialTheme.typography.labelMedium
                            )
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = "",
                                tint = Color(0xFFFFFF00),

                                )
                        }
                    }
                    if (userProfile.value != null) {
                        Button(
                            modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(Color(0xFFEF5350)),
                            shape = RoundedCornerShape(8.dp), onClick = {
                                authVm.signOut()

                            }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {

                                Text(
                                    text = "خروج از حساب",
                                    color = Color(0xFFFFFFFF),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}