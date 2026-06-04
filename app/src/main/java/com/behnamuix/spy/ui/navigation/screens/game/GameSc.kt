package com.behnamuix.spy.ui.navigation.screens.game

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.behnamuix.spy.R
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.ui.navigation.Screens
import com.behnamuix.spy.ui.navigation.screens.game.components.QCardComp
import com.behnamuix.spy.utils.setLog
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
    val ctx = LocalContext.current
    val isRunning = vm.isRunning.collectAsState()
    LaunchedEffect(Unit) {
        mediaVm.volumeLow()
        vm.setTime(time)
        setLog(time)
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
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    Toast.makeText(ctx, word, Toast.LENGTH_SHORT).show()
                },
            text = "زمان بحث و گفتگو",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(vm.questionList.toList().take(4)) {
                QCardComp(quiz = it.first, answer = it.second)
            }
        }
        TimerScreenComp(
            word = word,
            navController,
            mediaVm = mediaVm,
            vm = vm,
            isRunning = isRunning.value
        )

        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (isRunning.value) {
                IconButton(
                    modifier = Modifier.border(
                        1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        vm.stopTimer()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = ""
                    )
                }
            } else {
                IconButton(
                    modifier = Modifier.border(
                        1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        vm.resumeTimer()
                    }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = ""
                    )
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier,
                onClick = {

                    //harchi navigation ghabl az list bood remove mishe
                    navController.navigate(Screens.ConfigGame.route) {
                        popUpTo(Screens.ConfigGame.route) {
                            inclusive = true
                        }
                    }
                }

            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(8.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    text = "صفحه اصلی",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }

    }

}

@Composable
fun TimerScreenComp(
    word: String,
    navController: NavController,
    isRunning: Boolean,
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
    val infiniteAnimRotate = rememberInfiniteTransition()
    val rotate by infiniteAnimRotate.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Reverse,
            animation = tween(1500)
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        if (show) {
            AlertCardComp(mediaVm, navController, word)
        }
        Box(
            modifier = Modifier
                .width(vm.calcProgress().dp)
                .height(200.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        )


        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                color = Color.White,
                modifier = Modifier.padding(4.dp),
                text = vm.showTimerFormatedString(),
                style = MaterialTheme.typography.displayLarge
            )


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
                color = Color(0xFFFFFFFF),
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
                    color = Color(0xFFFFFFFF),
                    strokeWidth = 1.dp,
                    trackColor = Color.Transparent,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                )
            }


        }
    }
}

@Composable
fun AlertCardComp(
    mediaVm: MediaPlayerViewModel,
    navController: NavController,
    word: String,
    onDismiss: () -> Unit = {}
) {
    mediaVm.stop()

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // آیکون یا عکس جاسوس
                Icon(
                    painter = painterResource(R.drawable.img_spy),
                    contentDescription = "Spy",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    tint = Color.Unspecified
                )

                // عنوان
                Text(
                    text = " جاسوس ‌ها برنده شدند!️",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5252),
                    textAlign = TextAlign.Center
                )

                // خط جداکننده
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White.copy(0.3f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // متن توضیحی
                Text(
                    text = "کلمه بازی: $word",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "زمان تمام شد و مامورها نتوانستند جاسوس را پیدا کنند",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(0.6f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // دکمه
                Button(
                    onClick = {
                        navController.navigate(Screens.ConfigGame.route) {
                            popUpTo(Screens.ConfigGame.route) {
                                inclusive = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "بازی دوباره",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}






