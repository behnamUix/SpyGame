package com.behnamuix.spygame.core.media.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.behnamuix.spygame.core.media.presentation.contract.MusicPlayerContract
import com.behnamuix.spygame.core.media.presentation.viewmodel.MusicPlayerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestMediaScreen(
    viewModel: MusicPlayerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val listTrack = listOf(
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/01%20A%20Crucial%20Moment.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/04%20Political%20Tactics.mp3",
        "https://dl.songsara.net/FRE/2022/6/Gabriel%20Saban%20-%20Ambition%20-%20Political%20Orchestral%20Drama%20(2022)%20SONGSARA.NET/02%20Great%20Women.mp3"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Media Player Test Screen"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "State: ${uiState.playState}"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {
                viewModel.onAction(
                    MusicPlayerContract.UiAction.playRemote(
                        url = listTrack.random()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Play Sample Music")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    viewModel.onAction(
                        MusicPlayerContract.UiAction.pause
                    )
                }
            ) {
                Text(text = "Pause")
            }


            Button(
                onClick = {
                    viewModel.onAction(
                        MusicPlayerContract.UiAction.stop
                    )
                }
            ) {
                Text(text = "Stop")
            }
        }
    }
}