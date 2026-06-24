package com.behnamuix.spygame.utils

import android.util.Log

fun setLog(value: Any, tag: String = "LOG", type: String = "i") {
    when (type) {
        "d" -> Log.d(tag, value.toString())
        "e" -> Log.e(tag, value.toString())
        "i" -> Log.i(tag, value.toString())
        "w" -> Log.w(tag, value.toString())
        else -> Log.d(tag, value.toString()) // پیش‌فرض
    }
}