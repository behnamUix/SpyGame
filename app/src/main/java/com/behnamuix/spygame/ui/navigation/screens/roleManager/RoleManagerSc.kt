package com.behnamuix.spygame.ui.navigation.screens.roleManager
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.spygame.R
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.ui.navigation.screens.game.components.ToolbarComp
import com.behnamuix.spygame.ui.navigation.screens.roleManager.components.SpoilerComp
import com.behnamuix.spygame.ui.navigation.screens.roleManager.components.TimerCard
import com.behnamuix.spygame.utils.randomColor
import com.behnamuix.spygame.viewModel.RoleManagerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun RoleManagerSc(
    vm: RoleManagerViewModel = koinViewModel(),
    dataStoreVm: DataStoreViewModel = koinViewModel(),
    nextClick: (Int, String) -> Unit

) {
    val check by dataStoreVm.userUse.collectAsState()
    val timeLeft by vm.baseTimeInMinutes.collectAsState()
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scaleTrans = updateTransition(vm.stateScale)
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
    val newListPlayer = vm.playerList.collectAsState()

    LaunchedEffect(Unit, check) {
        vm.agentCount = dataStoreVm.agent.first()   // توجه: first() نیاز به import دارد

        vm.spyCount = dataStoreVm.spy.first()

        vm.configRole(true)

        if (check) {
            //use database
            vm.getKeyWord()
        } else {
            //use example
            vm.word = vm.category[(Random(System.currentTimeMillis()).nextInt(
                0,
                vm.category.size
            ))]

        }


    }

    Column(
        Modifier
            .fillMaxSize()
            .animateContentSize(tween(500)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ToolbarComp(" پخش نقش ها")


                /* test:

                                newListPlayer.value.forEach {
                                    Column {
                                        Text(it.id.toString(), color = Color.Yellow)
                                        Text(it.role, color = Color.Yellow)

                                    }
                                }*/

                LazyRow(
                    modifier = Modifier.padding(end = 8.dp),
                    reverseLayout = true,
                    userScrollEnabled = false,
                    state = scrollState
                ) {

                    itemsIndexed(newListPlayer.value) { index, item ->
                        PlayerCard(
                            vm.end, item.role, index,
                            vm.state, vm.word, vm.done
                        )


                    }
                }

                if (vm.showStartButton) {
                    TimerCard(
                        "زمان بازی",
                        vm,
                        timeLeft
                    )
                    Button(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(0.7f),
                        onClick = {

                            nextClick(vm.baseTimeInMinutes.value, vm.word)

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

                            val nextIndex = vm.currentCardIndex.intValue + 1
                            if (nextIndex < newListPlayer.value.size) {
                                vm.done = false
                                scope.launch {
                                    val job = launch {
                                        scrollState.animateScrollToItem(nextIndex)
                                        vm.currentCardIndex.intValue = nextIndex
                                    }
                                    job.invokeOnCompletion {
                                        vm.done = true
                                    }
                                    launch() {
                                        vm.stateScale = true
                                        delay(400)
                                        vm.stateScale = false

                                    }


                                }
                            } else {

                                vm.showStartButton = true
                                vm.end = true

                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),

                        ) {
                        Text(
                            text = "بده نفر بعدی",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(8.dp)
                                .testTag("bede_nafar_badi")
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun PlayerCard(
    end: Boolean,
    role: String,
    index: Int,
    state: MutableIntState,
    keyWord: String,
    done: Boolean,
) {
    val infiniteState = rememberInfiniteTransition()
    val infiniteStateEnd = rememberInfiniteTransition()
    val color by infiniteState.animateColor(
        initialValue = Color(0xFFFF5252),
        targetValue = Color(0xFFFFEB3B),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = EaseInOutBack),
            repeatMode = RepeatMode.Reverse
        )
    )
    val colorEnd by infiniteStateEnd.animateColor(
        initialValue = randomColor(),
        targetValue = randomColor(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(contentAlignment = Alignment.Center) {
        Card(
            border = BorderStroke(1.dp, Color.White.copy(0.5f)),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .size(380.dp)
                .padding(28.dp)
        ) {
            if (state.intValue == index) {
                Box(Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (end)
                    {
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
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .border(0.5.dp, color = colorEnd, shape = CircleShape),
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
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .border(1.dp, color = colorEnd, shape = CircleShape),

                                        painter = painterResource(R.drawable.img_agent),
                                        contentDescription = "",
                                    )
                                }


                            }


                        }
                    }
                    else
                    {
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
                                                color = color
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
                    SpoilerComp(state, index)
                }


            }
        }


    }
}









