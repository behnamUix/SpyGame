package com.behnamuix.spygame.feature.configgame.presentation.contract

import com.behnamuix.spygame.data.local.db.model.KeyWord

object ConfigGameContract {
    data class ConfigGameState(
        val wordList: List<KeyWord> = emptyList(),
        val expanded: Boolean = false,
        val agentCount: Int = 1,
        val spyCount: Int = 1,
        val agentCode: String = "",
        val spyCode: String = "",
        val enabled: Boolean = true,
        val wordExist: Boolean = false,
        val showAddWordDialog: Boolean = false,
        val progress: Boolean = true,
        val biometricSyncProg:Float=0f
    )

    sealed class ConfigGameAction {

        data class SetEnabled(val enabled: Boolean) : ConfigGameAction()

        data class AddWord(val word: KeyWord) : ConfigGameAction()
        data class DeleteWord(val id: Int) : ConfigGameAction()
        data object GetWords : ConfigGameAction()
        data class CheckWordExist(val word: String) : ConfigGameAction()

        data object IncreaseAgentCount : ConfigGameAction()
        data object DecreaseAgentCount : ConfigGameAction()
        data object IncreaseSpyCount : ConfigGameAction()
        data object DecreaseSpyCount : ConfigGameAction()

        data object ReverseExpand : ConfigGameAction()

        data class Initialize(
            val agentCount: Int,
            val spyCount: Int
        ) : ConfigGameAction()

        data object PlayMusic : ConfigGameAction()
        data object PauseMusic : ConfigGameAction()
        data object SetMusicVolume : ConfigGameAction()

        data object ShowAddWordDialog : ConfigGameAction()
        data object HideAddWordDialog : ConfigGameAction()

        data object setBiometricProgress: ConfigGameAction()

        data class SetProgress(val value: Boolean) : ConfigGameAction()
    }

}