package com.behnamuix.spy.ui.navigation.screens

import SpyGameSimulator.model.Player
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.retrofittest.R
import com.behnamuix.retrofittest.SpyGame.db.DatabaseProvider
import com.behnamuix.retrofittest.SpyGame.viewModel.ConfigRoleViewModel
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun RoleManagerSc(
    navController: NavController,
    userUse: Boolean,
    listPlayer: StateFlow<MutableList<Player>>,
    vm: ConfigRoleViewModel,
    gameViewModel: GameViewModel,
) {
    var word by remember { mutableStateOf("") }
    val ctx = LocalContext.current
    val dao = DatabaseProvider.getKeywordDao(ctx)
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var showStartButton by remember { mutableStateOf(false) }
    var showPage by remember { mutableStateOf("roleManager") }
    var newListPlayer = listPlayer.collectAsState()
    var done = remember { mutableStateOf(true) }
    var stateScale by remember { mutableStateOf(false) }
    val scaleTrans = updateTransition(stateScale)
    val scaleState by scaleTrans.animateFloat(transitionSpec = {
        spring(
            Spring.DampingRatioHighBouncy, Spring.StiffnessLow
        )
    }) {
        if (it) {
            1.1f
        } else {
            1f
        }
    }
    val timeLeft by gameViewModel.baseTimeInMinutes.collectAsState()


    LaunchedEffect(Unit) {
        val keyWord =
            if (userUse) {
                //use database
                vm.getKeyWord(
                    dao,
                    setWord = { word = it }
                ).toString()
            } else {
                //use example
                vm.category[(Random(System.currentTimeMillis()).nextInt(
                    0,
                    vm.category.size
                ))]

            }
        word = keyWord
    }
    var end = remember { mutableStateOf(false) }
    when (showPage) {

        "gameSc" -> {
            GameSc(gameViewModel)
        }

        "roleManager" -> {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            color = Color.White,
                            text = " پخش نقش ها",
                            style = MaterialTheme.typography.headlineSmall
                        )


//                        test:

//                        newListPlayer.value.forEach {
//                            Column {
//                                Text(it.id.toString(), color = Color.Yellow)
//                                Text(it.role, color = Color.Yellow)
//
//                            }
//                        }


                        LazyRow(
                            reverseLayout = true,
                            contentPadding = PaddingValues(horizontal = 24.dp),
                            userScrollEnabled = false,
                            state = scrollState
                        ) {

                            itemsIndexed(newListPlayer.value) { index, item ->
                                PlayerCard(
                                    end, item.role, index,
                                    vm.state, word, done
                                )


                            }
                        }

                        if (showStartButton) {
                            TimerCard(
                                "زمان بازی",
                                gameViewModel,
                                timeLeft
                            )
                            Button(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f),
                                onClick = {
                                    showPage = "gameSc"
                                }, colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))

                            ) {
                                Text(
                                    color = Color.White,

                                    text = "شروع بازی",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        } else {
                            Button(
                                elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
                                shape = RoundedCornerShape(16.dp),


                                modifier = Modifier
                                    .graphicsLayer(scaleX = scaleState)
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f),

                                onClick = {

                                    var nextIndex = vm.currentCardIndex.intValue + 1
                                    if (nextIndex < newListPlayer.value.size) {
                                        done.value = false
                                        scope.launch {
                                            var job = launch {
                                                scrollState.animateScrollToItem(nextIndex)
                                                vm.currentCardIndex.intValue = nextIndex
                                            }
                                            job.invokeOnCompletion {
                                                done.value = true
                                            }
                                            launch() {
                                                stateScale = true
                                                delay(400)
                                                stateScale = false

                                            }


                                        }
                                    } else {

                                        showStartButton = true
                                        end.value = true

                                    }
                                }, colors = ButtonDefaults.buttonColors(
                                    Color(0xFFE53935)

                                )
                            ) {
                                Text(
                                    text = "بده نفر بعدی",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun PlayerCard(
    end: MutableState<Boolean>,
    role: String,
    index: Int,
    state: MutableIntState,
    keyWord: String,
    done: MutableState<Boolean>,
) {
    var color = randomColor()


    Box(contentAlignment = Alignment.Center) {
        Card(
            border = BorderStroke(1.dp, Color.White.copy(0.5f)),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .size(380.dp)
                .padding(28.dp)
        ) {
            var ctx = LocalContext.current
            val infiniteAnim = rememberInfiniteTransition()
            val rotate by infiniteAnim.animateFloat(
                0f,
                targetValue = 360f,
                animationSpec = InfiniteRepeatableSpec(tween(1500), repeatMode = RepeatMode.Reverse)
            )
            val translateY by infiniteAnim.animateFloat(
                initialValue = -100f,
                targetValue = 100f,
                animationSpec = InfiniteRepeatableSpec(tween(2000), repeatMode = RepeatMode.Reverse)

            )

            if (state.intValue == index) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (end.value) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                                Box(Modifier.fillMaxSize()) {

                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(90f)
                                            .blur(60.dp),
                                        painter = painterResource(R.drawable.img_spy),
                                        contentDescription = ""
                                    )


                                }
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(start = 20.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Image(
                                        modifier = Modifier.clip(CircleShape),
                                        painter = painterResource(R.drawable.img_spy),
                                        contentDescription = "",

                                        )
                                }

                                Box(
                                    Modifier

                                        .fillMaxSize()
                                        .padding(end = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Image(
                                        modifier = Modifier.clip(CircleShape),

                                        painter = painterResource(R.drawable.img_agent),
                                        contentDescription = "",
                                    )
                                }
                                Box(
                                    Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Icon(
                                        painter =
                                            painterResource(R.drawable.gamepad),
                                        contentDescription = "",
                                        tint = color,
                                        modifier = Modifier
                                            .offset(x = -translateY.dp)
                                            .size(60.dp)
                                            .rotate(rotate)
                                    )
                                }

                            }


                        }
                    } else {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (role.contains("مامور")) {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(90f)
                                            .blur(40.dp),
                                        painter = painterResource(R.drawable.img_agent),
                                        contentDescription = ""
                                    )
                                    Box(
                                        Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .size(180.dp),
                                                painter = painterResource(R.drawable.img_agent),
                                                contentDescription = "",

                                                )
                                            Spacer(Modifier.height(16.dp))

                                            Text(
                                                text = role,
                                                style = MaterialTheme.typography.titleLarge,
                                                color = Color.White
                                            )
                                            Spacer(Modifier.height(16.dp))
                                            Text(
                                                text = " کلمه رمز: $keyWord",
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = Color(0xFFFFEB3B)
                                            )
                                        }
                                    }
                                }

                            } else {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(90f)
                                            .blur(40.dp),
                                        painter = painterResource(R.drawable.img_spy),
                                        contentDescription = ""
                                    )
                                    Box(
                                        Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .size(180.dp),
                                                painter = painterResource(R.drawable.img_spy),
                                                contentDescription = "",

                                                )
                                            Spacer(Modifier.height(16.dp))

                                            Text(role, fontSize = 24.sp, color = Color.White)
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SpoilerComp(state, index, done)

                }


            }

        }


    }
}


@Composable
fun SpoilerComp(
    state: MutableIntState,
    index: Int,
    done: MutableState<Boolean>
) {
    var ctx = LocalContext.current
    var scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .clickable(onClick = {
                state.intValue = index

            })


            .padding(2.dp)
            .fillMaxSize()
            .background(
                color = Color.White, shape = RoundedCornerShape(8.dp)
            )

    ) {

        Box(
            modifier = Modifier
                .clickable(onClick = {
                    state.intValue = index


                })
                .padding(32.dp)
                .fillMaxSize()
                .background(
                    color = Color.White, shape = RoundedCornerShape(8.dp)

                )


        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    " رو اینجا ضربه بزن تا نقشت رو نشون بدم",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


}

fun createUnpredictableShuffle(players: List<Player>): List<Player> {
    return players.shuffled()
}

fun randomColor(): Color {
    var r = Random.nextInt(0, 255)
    var b = Random.nextInt(0, 255)
    return Color(red = r, green = 255 / 2, blue = b)
}


@Composable
fun TimerCard(
    title: String,
    gameViewModel: GameViewModel,
    time: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            Color.Transparent
        ),
        enabled = true,
        onClick = {},
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),

            ) {

            Row(

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                TimerComp(
                    modifier = Modifier,
                    time,
                    gameViewModel
                )
                Spacer(Modifier.weight(1f))

                Text(title, modifier = Modifier.padding(end = 20.dp))
            }
        }


    }
}

@Composable
fun TimerComp(
    modifier: Modifier,
    time: Int,
    gameViewModel: GameViewModel
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            gameViewModel.decTime()
        }) {
            Text("—", fontSize = 20.sp)
        }
        Text(time.toString())
        IconButton({
            gameViewModel.incTime()
        }) {

            Icon(

                imageVector = Icons.Default.Add, contentDescription = ""
            )
        }
    }
}