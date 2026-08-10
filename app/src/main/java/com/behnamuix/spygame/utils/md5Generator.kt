package com.behnamuix.spygame.utils

import java.security.MessageDigest

fun generateMd5Code(count: Int): String {
    return try {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(count.toString().toByteArray())
        val hexString = digest.joinToString("") { "%02x".format(it) }
        hexString.take(4)
    } catch (e: Exception) {
        "0000"
    }
}