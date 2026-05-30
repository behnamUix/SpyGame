package com.behnamuix.spy.ui.navigation.screens.game

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.ui.navigation.Screens
import com.behnamuix.spy.ui.navigation.screens.game.components.QCardComp
import org.koin.androidx.compose.koinViewModel


@Composable
fun GameSc(
    navController: NavController,
    vm: GameViewModel = koinViewModel(),
    mediaVm: MediaPlayerViewModel = koinViewModel()
) {
    val isRunning = vm.isRunning.collectAsState()
    LaunchedEffect(Unit) {
        mediaVm.volumeLow()
        vm.startTimer()

    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp),
            text = "زمان بحث و گفتگو",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(vm.questionList.toList().take(4)) {
                QCardComp(quiz = it.first, answer = it.second)
            }
        }
        TimerScreenComp(
            mediaVm = mediaVm,
            vm = vm,
            showDialog = vm.showDialog,
            isRunning = isRunning.value
        )
        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier,
                onClick = {

                    //harchi navigation ghabl az list bood remove mishe
                    navController.navigate(Screens.ConfigGame.route) {
                        popUpTo(Screens.ConfigGame.route) {
                            inclusive = true
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(Color(0xFFEF5350))

            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    text = "صفحه اصلی",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }
        Button(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                vm.resetTimer()
            }, colors = ButtonDefaults.buttonColors(Color(0xFFFF9800))

        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                text = "ریست زمان",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

}

@Composable
fun TimerScreenComp(
    isRunning: Boolean,
    vm: GameViewModel,
    showDialog: Boolean,
    mediaVm: MediaPlayerViewModel
) {
    val infiniteAnimRotate = rememberInfiniteTransition()
    val rotate by infiniteAnimRotate.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Reverse,
            animation = tween(1500)
        )
    )
    if (showDialog) {
        AlertCardComp(mediaVm)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        CircularProgressIndicator(
            progress = { vm.calcProg() },
            modifier = Modifier
                .size(300.dp),
            color = Color(0xFF1E88E5),
            strokeWidth = 10.dp,
            trackColor = Color(0xFF90CAF9),
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                color = Color.White,
                modifier = Modifier.padding(4.dp),
                text = vm.showTimerFormatedString(),
                style = MaterialTheme.typography.displayMedium
            )
            Log.d("TIME", vm.showTimerFormatedString())

        }



        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            CircularProgressIndicator(
                progress = { 0.8f },
                modifier = Modifier
                    .size(80.dp)
                    .rotate(
                        if (isRunning) {
                            rotate
                        } else {
                            0f
                        }
                    )
                    .animateContentSize(tween(500)),
                color = Color(0xFF2196F3),
                strokeWidth = 1.dp,
                trackColor = Color.Transparent,
                strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
            )
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                CircularProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .size(80.dp)
                        .rotate(
                            if (isRunning) {
                                -rotate
                            } else {
                                0f
                            }
                        )
                        .animateContentSize(tween(500)),
                    color = Color(0xFF2196F3),
                    strokeWidth = 1.dp,
                    trackColor = Color.Transparent,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                )
            }


        }
    }
}

@Composable
fun AlertCardComp(mediaVm: MediaPlayerViewModel) {
    mediaVm.stop()
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { Button({}) { Text("باشه") } },
        text = { Text("زمان تمام شد و جاسوس ها برنده شدند") },
        title = { Text("توجه") })
}








