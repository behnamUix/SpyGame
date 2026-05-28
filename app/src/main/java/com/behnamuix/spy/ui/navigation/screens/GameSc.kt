package com.behnamuix.spy.ui.navigation.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.behnamuix.retrofittest.SpyGame.repository.SongController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSc(
    navController: NavController,
    gameViewModel: GameViewModel= koinViewModel()
) {

    gameViewModel.startTimer()


    var showDialog = remember { mutableStateOf(false) }
    var isRunning = gameViewModel.isRunning.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        LaunchedEffect(Unit) {
            SongController.setVolume(0.2f)
        }
        Text(
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp),
            text = "زمان بحث و گفتگو",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(gameViewModel.questionList.toList().take(4)) {

                QCardComp(quiz = it.first, answer = it.second)


            }
        }

        TimerScreenComp(
            gameViewModel = gameViewModel,
            showDialog = showDialog,
            isRunning = isRunning.value
        )
        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Button(
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier,
//                onClick = {
//                    gameViewModel.stopTimer()
//                }, colors = ButtonDefaults.buttonColors(Color(0xFFEF5350))
//
//            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(8.dp),
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    text = "توقف زمان",
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodyLarge,
//                )
//            }


//            if (!isRunning.value) {
//                Button(
//                    shape = RoundedCornerShape(16.dp),
//
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp),
//
//                    onClick = {
//                        gameViewModel.startTimer()
//
//
//                    }, colors = ButtonDefaults.buttonColors(Color(0xFF66BB6A))
//
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .padding(8.dp),
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        text = "ادامه بازی",
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.bodyLarge,
//                    )
//                }
//            }
        }
        Button(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                gameViewModel.resetTimer()
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
fun AlertCardComp(showDialog: MutableState<Boolean>) {
    SongController.stop()
    SongController.release()
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = { Button({ showDialog.value = false }) { Text("باشه") } },
        text = { Text("زمان تمام شد و جاسوس ها برنده شدند") },
        title = { Text("توجه") })
}


@Composable
fun QCardComp(quiz: String, answer: String) {
    var showAnswer by remember { mutableStateOf(false) }
    val transition = updateTransition(showAnswer)
    val rotateIcon by transition.animateFloat(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        )

    }) {
        if (it) {
            180f
        } else {
            0f
        }
    }
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(Color.White),
        onClick = {
            showAnswer = !showAnswer


        },
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(
                            rotateIcon
                        ),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = ""
                )
                Spacer(Modifier.weight(1f))

                Text(
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    text = quiz,
                    textAlign = TextAlign.Start,


                    )
            }
            AnimatedVisibility(showAnswer) {
                HorizontalDivider(Modifier.fillMaxWidth())
                Text(
                    color = Color.Black,

                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleSmall,
                    text = answer,
                    textAlign = TextAlign.Start,


                    )

            }
        }

    }


}


@Composable
fun TimerScreenComp(
    isRunning: Boolean,
    gameViewModel: GameViewModel,
    showDialog: MutableState<Boolean>
) {
    val infinitAnimRotate = rememberInfiniteTransition()
    val rotate by infinitAnimRotate.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Reverse,
            animation = tween(1500)
        )
    )
    if (showDialog.value) {
        AlertCardComp(showDialog)
    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        CircularProgressIndicator(
            progress = { gameViewModel.secondsLeft.value.toFloat() / gameViewModel.initialSeconds.toFloat() },
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
                text = gameViewModel.showTimerFormatedString(),
                style = MaterialTheme.typography.displayMedium
            )
            Log.d("TIME", gameViewModel.showTimerFormatedString())

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


