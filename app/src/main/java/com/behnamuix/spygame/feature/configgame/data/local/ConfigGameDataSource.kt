package com.behnamuix.spygame.feature.configgame.data.local

import android.util.Log
import com.behnamuix.spygame.feature.configgame.domain.model.Agent
import com.behnamuix.spygame.feature.configgame.domain.model.Biometric
import com.behnamuix.spygame.feature.configgame.domain.model.Spy
import com.behnamuix.spygame.utils.generateMd5Code
import kotlin.random.Random


class ConfigGameDataSource {

    fun incAgentCountPlayer() {
        Agent.count++
        Agent.code=generateMd5Code(Agent.count)

    }

    fun decAgentCountPlayer() {
        Agent.count--
        Agent.code=generateMd5Code(Agent.count)

    }

    fun incSpyCountPlayer(){
        Spy.count++
        Spy.code=generateMd5Code(Spy.count)


    }

    fun decSpyCountPlayer() {
        Spy.count--
        Spy.code=generateMd5Code(Spy.count)

    }
    fun generateBiometricProg(){
        Biometric.prog= Random.nextFloat()
    }
}
