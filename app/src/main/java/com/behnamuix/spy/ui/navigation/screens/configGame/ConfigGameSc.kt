package com.behnamuix.spy.ui.navigation.screens.configGame

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.behnamuix.spy.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spy.ui.navigation.Screens
import com.behnamuix.spy.ui.navigation.screens.configGame.components.AddKeyWordAlert
import com.behnamuix.spy.ui.navigation.screens.configGame.components.InformationComp
import com.behnamuix.spy.ui.navigation.screens.configGame.components.MediaControllerComp
import com.behnamuix.spy.ui.navigation.screens.training.components.AgentComp
import com.behnamuix.spy.ui.navigation.screens.training.components.SpyComp
import com.behnamuix.spy.utils.checkNet
import com.behnamuix.spy.utils.setLog
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import com.behnamuix.spy.viewModel.MediaState
import com.behnamuix.spy.viewModel.RoleManagerViewModel
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfigGameSc(
    navController: NavController,
    vm: ConfigGameViewModel = koinViewModel(),
    roleManagerViewModel: RoleManagerViewModel = koinViewModel(),
    dsVm: DataStoreViewModel = koinViewModel()

) {
    //context
    val ctx = LocalContext.current

    val listWord by vm.wordList.collectAsState()

    var expandedState = vm.expanded.collectAsState()

    val rotationAngle by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        ), label = "rotation" // اضافه کردن label برای بهتر شدن لاگ‌ها
    )

    val agentCount by vm.agentCount.collectAsState()

    val spyCount by vm.spyCount.collectAsState()

    val mediaState by vm.mediaState.collectAsState()

    val enabled = vm.enabled.collectAsState()



    LaunchedEffect(Unit) {
        vm.init(dsVm.agent.first(), dsVm.spy.first())
        vm.getWords()
        if (ctx.checkNet() == false) {
            Toast.makeText(ctx, "اینترنت قطع است!", Toast.LENGTH_SHORT).show()
        } else {
            vm.setVolume()
            //vm.play()


        }


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
        Header(
            dsVm = dsVm,
            mediaState,
            vm,
            rotationAngle,
            expandedState,
            ctx
        )
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
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White.copy(0.5f),
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                    Row {
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
                        Spacer(Modifier.weight(1f))
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
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    onClick = {
                        navController.navigate(Screens.RoleManager.route)

                    }) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = " برو بعدی (${roleManagerViewModel.category.size.toString()} کلمه)",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(12.dp)
                            .testTag("boro_badi")
                    )
                }
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        " 1405-02-05 برطرف شدن باگ پخش نقش ها الگوریتم رندوم تر دوست عزیزم رضا",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,

                        )
                    Text(
                        "1405-01-28 برطرف شدن باگ لو رفتن نقش با تشکر از دوست عزیزم سیاوش",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,

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

@Composable
fun Header(
    dsVm: DataStoreViewModel,
    mediaState: MediaState,
    vm: ConfigGameViewModel,
    rotationAngle: Float,
    expandedState: State<Boolean>,
    ctx: Context
) {
    var check by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        vm.userUseOperation(dsVm, setCheck = { check = it })

    }
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(8.dp), onClick = {
                vm.reverseExpand()
            }) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Default.Settings,
                modifier = Modifier
                    .size(32.dp)

                    .rotate(rotationAngle),
                contentDescription = ""
            )
        }

        Spacer(Modifier.weight(1f))

        MediaControllerComp(mediaState, vm)
    }
    AnimatedVisibility(
        expandedState.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        OutlinedCard(Modifier.padding(16.dp)) {
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.End,
                    text = "میخوای بازی از کلمات تو استفاده کنه",
                    modifier = Modifier.weight(0.8f)
                )
                Checkbox(
                    checked = check,
                    onCheckedChange = { isChecked ->
                        dsVm.setUserUse(isChecked)
                        check = isChecked
                        setLog(value = check)

                        if (check && vm.checkDb()) {
                            vm.setEnabled(false)
                            Toast.makeText(ctx, "لیست کلماتت خالیه", Toast.LENGTH_SHORT).show()
                        } else {
                            vm.setEnabled(true)
                        }
                    }
                )
            }
        }
    }
}












