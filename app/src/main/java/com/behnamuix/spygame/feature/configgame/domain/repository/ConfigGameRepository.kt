package com.behnamuix.spygame.feature.configgame.domain.repository

import com.behnamuix.spygame.feature.configgame.domain.model.Agent
import com.behnamuix.spygame.feature.configgame.domain.model.Spy

interface ConfigGameRepository {

    fun incAgentCountPlayer():Int
    fun decAgentCountPlayer():Int

    fun incSpyCountPlayer():Int
    fun decSpyCountPlayer():Int

    fun getAgentCode(): String
    fun getSpyCode():String

    fun getBioProg():Float
}