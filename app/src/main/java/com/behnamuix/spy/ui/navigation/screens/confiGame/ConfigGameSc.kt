package com.behnamuix.spy.ui.navigation.screens.confiGame

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import com.behnamuix.spy.viewModel.RoleManagerViewModel
import com.behnamuix.spy.data.local.db.model.KeyWord
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.ui.navigation.Screens
import com.behnamuix.spy.ui.navigation.screens.training.components.AgentComp
import com.behnamuix.spy.ui.navigation.screens.confiGame.components.InformationComp
import com.behnamuix.spy.ui.navigation.screens.confiGame.components.ListKeyWordsComp
import com.behnamuix.spy.ui.navigation.screens.training.components.SpyComp
import com.behnamuix.spy.utils.checkNet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfigGameSc(
    navController: NavController,
    vm: ConfigGameViewModel = koinViewModel(),
    roleManagerViewModel: RoleManagerViewModel = koinViewModel(),
    mediaVm: MediaPlayerViewModel = koinViewModel()
) {
    //context
    val ctx = LocalContext.current

    val listWord by vm.wordList.collectAsState()

    var expandedState = vm.expanded.collectAsState()

    val rotationAngle by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "rotation" // اضافه کردن label برای بهتر شدن لاگ‌ها
    )

    val agentCount by vm.agentCount.collectAsState()

    val spyCount by vm.spyCount.collectAsState()

    LaunchedEffect(Unit) {
        vm.getWords()
        if (ctx.checkNet() == false) {
            Toast.makeText(ctx, "اینترنت قطع است!", Toast.LENGTH_SHORT).show()
        }
        mediaVm.play()

    }
    Column(
        Modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        Header(vm, rotationAngle, expandedState, ctx)
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
                    "تعداد جاسوسان",
                    spyCount,
                    vm
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
                        modifier = Modifier.fillMaxWidth(), fontSize = 14.sp
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

                            onClick = { navController.navigate(Screens.Training.route) }
                        ) {
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
                            onClick = { vm.showAddWordDialog.value = true }
                        ) {
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
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFE53935)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    onClick = {
                        Log.d("TAG", "${agentCount.toString()}/${spyCount.toString()}")
                        navController.navigate(Screens.RoleManager.route)

                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = " برو بعدی (${roleManagerViewModel.category.size.toString()} کلمه)",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(12.dp)
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
    vm: ConfigGameViewModel,
    rotationAngle: Float,
    expandedState: State<Boolean>,
    ctx: Context
) {
    IconButton(
        modifier = Modifier.padding(8.dp),
        onClick = {
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
    AnimatedVisibility(
        expandedState.value, enter = fadeIn(), exit = fadeOut()
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


                    checked = vm.userUse,
                    onCheckedChange = {
                        vm.userUse = it
                        if (it) {
                            if (vm.checkDb()) {
                                Toast.makeText(
                                    ctx,
                                    "لیست کلماتت  خالی است ",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }


                    })
            }
        }
    }
}

@Composable
fun AddKeyWordAlert(
    showAddWordDialog: MutableState<Boolean>,
    configGameViewModel: ConfigGameViewModel,
    ctx: Context,
    listWord: List<KeyWord>,
) {
    val scope = rememberCoroutineScope()
    var word by remember { mutableStateOf("") }
    var wordExist by remember { mutableStateOf(configGameViewModel.wordExist.value) }
    Dialog(
        { showAddWordDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(colors = CardDefaults.cardColors(Color.Black)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    text = "هر کلمه ای بخوای میتونی به بازی اضافه کنی و از بازی بیشتر لذت ببری",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    isError = wordExist,
                    supportingText = { if (wordExist) Text(" ببخشید کلمه $word در بازی وجود داره:( ") },
                    textStyle = TextStyle(
                        textDirection = TextDirection.Rtl,
                        textAlign = TextAlign.Right
                    ),
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "کلمه را وارد کنید",
                            textAlign = TextAlign.Right
                        )
                    },
                    value = word,
                    onValueChange = { word = it }
                )
                Row(Modifier.padding(horizontal = 16.dp)) {
                    Button(
                        onClick = {

                            showAddWordDialog.value = false
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFEF5350)),
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "بیخیال",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.width(16.dp))
                    Button(
                        modifier = Modifier.weight(0.6f),
                        onClick = {
                            scope.launch {
                                if (word.isNotEmpty()) {
                                    if (!wordExist) {
                                        configGameViewModel.addWord(
                                            KeyWord(word = word)
                                        )
                                        word = ""

                                        Toast.makeText(
                                            ctx,
                                            " کلمه $word به بازی اضافه شد ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                }
                            }


                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF66BB6A)),
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "اضافه کن",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Black.copy(0.2f),
                    modifier = Modifier.padding(8.dp)
                )
                ListKeyWordsComp(configGameViewModel, listWord)
            }
        }
    }
}









