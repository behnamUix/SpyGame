package com.behnamuix.spy.ui.navigation.screens.confiGame.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun InformationComp() {
    val infiniteTrans = rememberInfiniteTransition()
    val translateY by infiniteTrans.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )
    var ctx = LocalContext.current
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
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("طراحی و برنامه نویسی:BehnamUix")
                Text("نسخه آلفا 3")
                OutlinedButton(
                    border = BorderStroke(
                        1.dp, color = Color(0xFFFFFF00),
                    ),
                    shape = RoundedCornerShape(8.dp), onClick = {
                        val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                            Intent.setData = "https://t.me/behnamUix".toUri()
                            Intent.setPackage = "org.telegram.messenger"
                        }
                        try {
                            ctx.startActivity(telegramIntent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(ctx, "تلگرام نصب نیست!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                    Text(
                        text = "پشتیبانی",
                        color = Color(0xFFFFFF00),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }


    }
}