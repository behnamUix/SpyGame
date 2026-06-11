package com.behnamuix.spygame.utils

import kotlin.random.Random

fun getRandom(value: Int): Int {
    return Random(System.currentTimeMillis()).nextInt(
        0,
        value
    )
}