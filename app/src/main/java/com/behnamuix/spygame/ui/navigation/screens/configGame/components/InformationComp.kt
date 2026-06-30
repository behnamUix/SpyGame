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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

    val ctx = LocalContext.current

    val versionName = try {
        val packageInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
        packageInfo.versionName
    } catch (e: Exception) {
        "1.0.0"
    }

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


            .fillMaxWidth()
            .offset(x = 0.dp, y = translateY.dp)
            .padding(16.dp), contentAlignment = Alignment.TopCenter
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
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    IconButton({
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
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = "",
                            tint = Color.White


                            )
                    }

                    IconButton({
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "بازی مهیج جاسوس")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "ببینیم کی می‌تونه جاسوس رو لو بده! 😎\nهمین الان بازی جاسوس رو از مایکت دانلود کن و دورهمی‌هات رو هیجانی‌تر کن:\nhttps://myket.ir/app/com.behnamuix.spygame"
                            )
                        }
                        ctx.startActivity(
                            Intent.createChooser(shareIntent, "اشتراک گذاری از طریق:")
                        )
                    }) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = Icons.Default.Share,
                            contentDescription = "",
                            tint = Color(0xFFEF5350),


                            )
                    }


                }
            }



    }
}