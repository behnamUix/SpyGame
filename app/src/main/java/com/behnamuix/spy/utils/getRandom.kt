package com.behnamuix.spy.utils

import kotlin.random.Random

fun getRandom(value: Int): Int {
    return Random(System.currentTimeMillis()).nextInt(
        0,
        value
    )
}