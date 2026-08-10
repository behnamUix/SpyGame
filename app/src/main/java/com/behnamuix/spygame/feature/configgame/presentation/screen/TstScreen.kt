package com.behnamuix.spygame.feature.configgame.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.behnamuix.spygame.feature.configgame.domain.usecase.ConfigGameUseCase
import com.behnamuix.spygame.feature.configgame.presentation.viewmodel.ConfigGameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestSc( configGameViewModel: ConfigGameViewModel=koinViewModel()) {
    val agentCount=configGameViewModel.agentCount.collectAsState()
    val spyCount=configGameViewModel.spyCount.collectAsState()

    val agentCode=configGameViewModel.agentCode.collectAsState()
    val spyCode=configGameViewModel.spyCode.collectAsState()
    Column() {
        Text("agentCount:${agentCount.value}\ncode:${agentCode.value}")
        Text("spyCount:${spyCount.value}\ncode:${spyCode.value}")
        Row() {
            Button(
                { configGameViewModel.decSpyCountPlayer() }
            ) {
                Text("decSpy")
            }
            Button(
                {configGameViewModel.incSpyCountPlayer() }
            ) {
                Text("incSpy")
            }
        }
        Row() {
            Button(
                { configGameViewModel.decAgentCountPlayer() }
            ) {
                Text("decAgent")
            }
            Button(
                { configGameViewModel.incAgentCountPlayer() }
            ) {
                Text("incAgent")
            }
        }


    }

}