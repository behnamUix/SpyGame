package com.behnamuix.spygame.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun randomColor(): Color {
    var r = Random.nextInt(0, 255)
    var b = Random.nextInt(0, 255)
    var g = Random.nextInt(0, 255)
    return Color(red = r, green = g, blue = b)
}