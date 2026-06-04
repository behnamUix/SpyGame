package com.behnamuix.spy.ui.navigation.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.viewModel.GameViewModel

@Composable
fun TimerScreenComp(

    progress: Float,
    formattedTime: String,
    word: String,
    navController: NavController,
    vm: GameViewModel,
    mediaVm: MediaPlayerViewModel
) {

    var show by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        vm.showDialog.collect {
            if (it) {
                show = true
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        if (show) {
            AlertCardComp(mediaVm, navController, word)
        }

        //WaveProgress
        WaterTankProgressBar(progress = progress)

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                color = Color.White,
                modifier = Modifier.padding(4.dp),
                text = formattedTime,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}