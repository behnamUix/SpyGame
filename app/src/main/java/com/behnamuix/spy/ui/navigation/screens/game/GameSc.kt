package com.behnamuix.spy.ui.navigation.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.ui.navigation.screens.game.components.BottomBarComp
import com.behnamuix.spy.ui.navigation.screens.game.components.QCardComp
import com.behnamuix.spy.ui.navigation.screens.game.components.TimerScreenComp
import com.behnamuix.spy.ui.navigation.screens.game.components.ToolbarComp
import com.behnamuix.spy.viewModel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameSc(
    word: String,
    time: Int,
    navController: NavController,
    vm: GameViewModel = koinViewModel(),
    mediaVm: MediaPlayerViewModel = koinViewModel()
) {
    val secondsLeft by vm.secondsLeft.collectAsState()
    val isRunning by vm.isRunning.collectAsState()
    LaunchedEffect(Unit) {
        mediaVm.volumeLow()
        vm.setTime(time)
        vm.startTimer()
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        ToolbarComp("زمان بحث و گفتگو", word)

        LazyColumn {
            items(vm.questionList.toList().take(4)) {
                QCardComp(quiz = it.first, answer = it.second)
            }
        }
        TimerScreenComp(
            progress = vm.calcProgress(secondsLeft),
            formattedTime = vm.showTimerFormatedString(secondsLeft),
            word = word,
            navController = navController,
            mediaVm = mediaVm,
            vm = vm
        )
        BottomBarComp(navController, vm, isRunning)
    }
}



