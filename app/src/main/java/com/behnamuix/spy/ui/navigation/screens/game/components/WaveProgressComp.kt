package com.behnamuix.spy.ui.navigation.screens.game.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun WaterProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    waterColor: Color = Color(0xFF2196F3),
    tankColor: Color = Color(0xFFE0E0E0)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "NaturalWater")

    // ۱. انیمیشن موج لایه اول (جلو)
    val waveOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WaveOffset1"
    )

    // ۲. انیمیشن موج لایه دوم (عقب) با سرعت متفاوت
    val waveOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WaveOffset2"
    )

    // ۳. انیمیشن گهواره‌ای (تکان خوردن کل توده آب به چپ و راست)
    val sloshOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "SloshOffset"
    )

    // انیمیشن نرم برای حرکت عمودی سطح آب
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "ProgressAnimation"
    )

    // انیمیشن سریع برای جریان خروج آب و حرکت قطرات
    val leakOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing), // سریع‌تر برای حس شتاب فوران
            repeatMode = RepeatMode.Restart
        ),
        label = "LeakOffset"
    )

    Canvas(modifier = modifier.size(300.dp)) {
        val width = size.width
        val height = size.height
        val radius = width / 2

        // الف) رسم شیشه دایره‌ای پشت ظرف (شفافیت ملایم)
        drawCircle(
            color = tankColor.copy(alpha = 0.15f),
            radius = radius
        )

        // ب) ایجاد ماسک برش برای محدود کردن آب به داخل دایره تنگ
        val containerPath = Path().apply {
            addOval(Rect(0f, 0f, width, height))
        }
        val waterLevelY = height * (1f - animatedProgress)

        clipPath(containerPath) {
            val baseAmplitude = 10f
            val frequency1 = 0.015f
            val frequency2 = 0.035f

            // 🌊 لایه اول: آب پس‌زمینه (تیره و عمیق)
            val backWavePath = Path().apply {
                moveTo(0f, height)
                lineTo(0f, waterLevelY)
                for (x in 0..width.toInt()) {
                    val sloshFactor = (x - width / 2) / width * sloshOffset
                    val y = waterLevelY +
                            sin(x * frequency1 - waveOffset2) * baseAmplitude +
                            sin(x * frequency2 + waveOffset2) * (baseAmplitude * 0.3f) +
                            sloshFactor
                    lineTo(x.toFloat(), y)
                }
                lineTo(width, height)
                close()
            }
            drawPath(path = backWavePath, color = waterColor.copy(alpha = 0.35f))

            // 🌊 لایه دوم: بدنه اصلی آب (رنگ استاندارد)
            val middleWavePath = Path().apply {
                moveTo(0f, height)
                lineTo(0f, waterLevelY)
                for (x in 0..width.toInt()) {
                    val sloshFactor = (x - width / 2) / width * -sloshOffset
                    val y = waterLevelY +
                            sin(x * frequency1 + waveOffset1) * baseAmplitude +
                            sin(x * frequency2 - waveOffset1) * (baseAmplitude * 0.2f) +
                            sloshFactor
                    lineTo(x.toFloat(), y)
                }
                lineTo(width, height)
                close()
            }
            drawPath(path = middleWavePath, color = waterColor.copy(alpha = 0.8f))

            // 🌊 لایه سوم: درخشش روی سطح آب (Highlight)
            val frontHighlightPath = Path().apply {
                moveTo(0f, height)
                lineTo(0f, waterLevelY)
                for (x in 0..width.toInt()) {
                    val sloshFactor = (x - width / 2) / width * -sloshOffset
                    val y = (waterLevelY + 3f) +
                            sin(x * frequency1 + waveOffset1) * baseAmplitude +
                            sin(x * frequency2 - waveOffset1) * (baseAmplitude * 0.2f) +
                            sloshFactor
                    lineTo(x.toFloat(), y)
                }
                lineTo(width, height)
                close()
            }
            drawPath(path = frontHighlightPath, color = Color.White.copy(alpha = 0.25f))
        }

        // ج) رسم هاله شیشه‌ای روی کل دایره
        drawCircle(
            color = Color.White.copy(alpha = 0.1f),
            radius = radius,
            style = Stroke(width = 3.dp.toPx())
        )

        // 💥 د) شبیه‌سازی سوراخ شکستگی و خروج آب پویا (Dynamic Leakage)
        val leakX = width-80f
        val leakY = height * 0.8f

        // کشیدن نقطه شکستگی شیشه
        drawCircle(
            color = Color.White.copy(alpha = 0.7f),
            radius = 4.dp.toPx(),
            center = Offset(leakX - 2.dp.toPx(), leakY)
        )

        // خروج آب با منطق فشار هیدرواستاتیکی کاملاً داینامیک
        if (waterLevelY < leakY) {

            // ۱. محاسبه میزان فشار آب: فاصله عمودی سطح آب تا سوراخ
            val waterHeadPressure = ((leakY - waterLevelY) / height).coerceIn(0f, 1f)
            // برد پرتاب افقی متناسب با مقدار فشار فعلی آب تنظیم می‌شود (حداکثر ۷۰ پیکسل)
            val maxHorizontalBlast = 70f * waterHeadPressure

            val streamPath = Path().apply {
                moveTo(leakX, leakY)

                val streamBottomY = height + 60f // شلاق آب تا کمی پایین‌تر از تانک سقوط کند
                var currentY = leakY

                while (currentY <= streamBottomY) {
                    val progressFactor = (currentY - leakY) / (streamBottomY - leakY)

                    // استفاده از تابع سینوسی نیم‌قوس برای اعمال شتاب گرانش زمین نیوتن (سقوط تیزتر در انتها)
                    val parabolaX = leakX + (kotlin.math.sin(progressFactor * Math.PI / 2) * maxHorizontalBlast).toFloat()

                    // تلاطم و موج‌های ریز داخل جت خروجی آب با فرکانس بالا (پایه نازک و انتهای پخش‌تر)
                    val waveX = parabolaX + sin(currentY * 0.15f - leakOffset * 2.5f) * (1.5f + progressFactor * 3.5f)

                    lineTo(waveX, currentY)
                    currentY += 4f
                }
            }

            // ۲. رسم جت اصلی آب (بدنه ضخیم با انتهای نرم)
            drawPath(
                path = streamPath,
                color = waterColor.copy(alpha = 0.75f),
                style = Stroke(width = 4.5.dp.toPx(), cap = StrokeCap.Round)
            )

            // رسم هسته نوری و براق داخل جت آب
            drawPath(
                path = streamPath,
                color = Color.White.copy(alpha = 0.45f),
                style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
            )

            // ۳. 🛑 افکت پاشش قطرات پراکنده (Water Droplets/Spray) در نقطه فرود
            val sprayBaseY = height + 10f
            if (maxHorizontalBlast > 8f) { // فقط زمانی که فشار کافی وجود دارد قطرات معلق ایجاد شوند
                for (i in 1..4) {
                    // تغییر موقعیت لحظه‌ای قطرات با فرکانس فریم ریت انیمیشن
                    val dropletAnimFactor = ((leakOffset + (i * 1.5f)) % (2 * Math.PI.toFloat()))
                    val dropY = sprayBaseY + (i * 10f) + sin(dropletAnimFactor) * 8f
                    val dropX = leakX + maxHorizontalBlast + (sin(i.toFloat() * 25f) * 14f) + (dropletAnimFactor * 3f)

                    if (dropY <= height + 55f) {
                        // قطره اصلی آب
                        drawCircle(
                            color = waterColor.copy(alpha = 0.55f),
                            radius = (1.5f + (i % 2)).dp.toPx(),
                            center = Offset(dropX, dropY)
                        )
                        // درخشش روی قطره کوچک آب
                        drawCircle(
                            color = Color.White.copy(alpha = 0.7f),
                            radius = 0.6.dp.toPx(),
                            center = Offset(dropX - 1f, dropY - 1f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SampleWaterTankProgressBar(
    progress: Float, // مقداری بین 0.0 تا 1.0
    modifier: Modifier = Modifier,
    waterColor: Color = Color(0xFF2196F3),
    tankColor: Color = Color(0xFFE0E0E0)
) {
    // انیمیشن نرم برای بالا و پایین رفتن سطح آب
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "SimpleProgress"
    )

    Canvas(modifier = modifier.size(300.dp)) {
        val width = size.width
        val height = size.height
        val radius = width / 2

        // ۱. رسم پس‌زمینه کم‌رنگ دایره (بدنه شیشه‌ای تنگ)
        drawCircle(
            color = tankColor.copy(alpha = 0.15f),
            radius = radius
        )

        // ۲. ایجاد ماسک دایره‌ای برای اینکه آب از محیط تنگ بیرون نزند
        val containerPath = Path().apply {
            addOval(Rect(0f, 0f, width, height))
        }

        clipPath(containerPath) {
            // محاسبه خط افقی ارتفاع آب
            val waterLevelY = height * (1f - animatedProgress)

            // رسم توده آب به صورت یک مستطیل ساده از سطح آب تا کف ظرف
            val waterPath = Path().apply {
                moveTo(0f, waterLevelY)         // نقطه شروع سمت چپ روی سطح آب
                lineTo(width, waterLevelY)      // خط صاف به سمت راست سطح آب
                lineTo(width, height)          // خط به کف سمت راست
                lineTo(0f, height)             // خط به کف سمت چپ
                close()                        // بستن مسیر
            }

            drawPath(
                path = waterPath,
                color = waterColor
            )
        }

        // ۳. رسم خط دور شیشه تنگ (مرز دایره)
        drawCircle(
            color = Color.White.copy(alpha = 0.2f),
            radius = radius,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
        )
    }
}