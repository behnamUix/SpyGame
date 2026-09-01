package com.behnamuix.spygame.feature.configgame.data.repository

import com.behnamuix.spygame.feature.configgame.data.local.ConfigGameDataSource
import com.behnamuix.spygame.feature.configgame.domain.model.Agent
import com.behnamuix.spygame.feature.configgame.domain.model.Biometric
import com.behnamuix.spygame.feature.configgame.domain.model.Spy
import com.behnamuix.spygame.feature.configgame.domain.repository.ConfigGameRepository


class ConfigGameRepositoryImpl :
    ConfigGameRepository {

    private val configGameDataSource: ConfigGameDataSource

    constructor(configGameDataSource: ConfigGameDataSource) {
        this.configGameDataSource = configGameDataSource
    }

    override fun incAgentCountPlayer(): Int {
        configGameDataSource.incAgentCountPlayer()
        return Agent.count

    }

    override fun decAgentCountPlayer(): Int {

        configGameDataSource.decAgentCountPlayer()
        return Agent.count

    }

    override fun incSpyCountPlayer(): Int {
        configGameDataSource.incSpyCountPlayer()
        return Spy.count

    }

    override fun decSpyCountPlayer(): Int {
        configGameDataSource.decSpyCountPlayer()
        return Spy.count

    }

    override fun getAgentCode(): String {
        return Agent.code
    }

    override fun getSpyCode(): String {
        return Spy.code
    }

    override fun getBioProg(): Float {
        configGameDataSource.generateBiometricProg()
        return Biometric.prog
    }


}