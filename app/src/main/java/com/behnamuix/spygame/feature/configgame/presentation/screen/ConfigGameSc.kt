package com.behnamuix.spygame.feature.configgame.presentation.screen


import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.behnamuix.spygame.R
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.core.media.presentation.viewmodel.MusicPlayerViewModel
import com.behnamuix.spygame.feature.configgame.presentation.screen.components.AddKeyWordAlert
import com.behnamuix.spygame.feature.configgame.presentation.screen.components.Header
import com.behnamuix.spygame.ui.navigation.Screens
import com.behnamuix.spygame.feature.configgame.presentation.screen.components.InformationComp
import com.behnamuix.spygame.ui.navigation.screens.training.components.AgentComp
import com.behnamuix.spygame.ui.navigation.screens.training.components.SpyComp
import com.behnamuix.spygame.feature.configgame.presentation.viewmodel.ConfigGameViewModel
import com.behnamuix.spygame.viewModel.RoleManagerViewModel
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigGameSc(
    navController: NavController,
    vm: ConfigGameViewModel = koinViewModel(),
    mediaVm: MusicPlayerViewModel = koinViewModel(),
    roleManagerViewModel: RoleManagerViewModel = koinViewModel(),
    dsVm: DataStoreViewModel = koinViewModel(),


    ) {
    //context
    val ctx = LocalContext.current

    val listWord by vm.wordList.collectAsState()

    val agentCount by vm.agentCount.collectAsState()

    val spyCount by vm.spyCount.collectAsState()

    val enabled = vm.enabled.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "color")
    val infiniteTransition2 = rememberInfiniteTransition(label = "color")
    val colorAnimate1 by infiniteTransition.animateColor(
        initialValue = Color(0xFFEF5350),
        targetValue = Color(0xFFFFEE58),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "angle"
    )
    val colorAnimate2 by infiniteTransition2.animateColor(
        initialValue = Color(0xFF42A5F5),
        targetValue = Color(0xFF66BB6A),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    LaunchedEffect(Unit) {
        //mediaVm.volumeHigh()
        vm.init(dsVm.agent.first(), dsVm.spy.first())
        vm.getWords()

    }
    LaunchedEffect(agentCount, spyCount) {
        dsVm.setAgent(agentCount)
        dsVm.setSpy(spyCount)
    }

    Column(
        Modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        Header(navController)
        Spacer(Modifier.height(24.dp))
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier

                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.End
            ) {
                AgentComp(
                    title = "تعداد ماموران",
                    agentCount = agentCount,
                    vm,
                )
                SpyComp(
                    "تعداد جاسوسان", spyCount, vm
                )

                if (listWord.isNotEmpty()) {
                    vm.progress = false
                    Text(
                        "پایگاه داده بازی آماده است!",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF64DD17),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        //training
                        OutlinedButton(
                            border = BorderStroke(1.dp, Color(0xFF03A9F4)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier,

                            onClick = { navController.navigate(Screens.Training.route) }) {
                            Text(
                                text = "آموزش بازی",
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        //َAi
                        Button(
                            onClick = {
                                Toast.makeText(ctx, "به زودی ...", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            modifier = Modifier

                                .border(
                                    width = 4.dp,
                                    brush = Brush.linearGradient(
                                        listOf(
                                            colorAnimate1,
                                            colorAnimate2
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_ai),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = Color.Black
                            )

                        }

                        //addWord
                        OutlinedButton(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier,
                            onClick = { vm.showAddWordDialog.value = true }) {
                            Text(
                                text = "کلمات شما",
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }


                    }

                }






                Button(
                    enabled = enabled.value,
                    shape = RoundedCornerShape(8.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primary
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    onClick = {
                        navController.navigate(Screens.RoleManager.route)

                    }) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = " شروع بازی (${roleManagerViewModel.category.size} کلمه)",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(12.dp)
                            .testTag("boro_badi")
                    )
                }





                InformationComp()
                //Alert
                if (vm.showAddWordDialog.value) {
                    AddKeyWordAlert(
                        vm.showAddWordDialog,
                        vm,
                        ctx,
                        listWord

                    )
                }
            }
        }

    }

}















