package com.behnamuix.spy.utils

import SpyGameSimulator.model.Player

fun createUnpredictableShuffle(players: List<Player>): List<Player> {
    return players.shuffled()
}