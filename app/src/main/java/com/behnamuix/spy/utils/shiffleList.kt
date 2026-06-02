package com.behnamuix.spy.utils

import SpyGameSimulator.model.Player

fun shuffledList(useSecureRandom: Boolean, list: MutableList<Player>): MutableList<Player> {
    return if (useSecureRandom) {
        list.shuffled(java.security.SecureRandom())
    } else {
        list.shuffled()
    }.toMutableList()

}