package com.behnamuix.spy.ui.navigation.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import com.behnamuix.retrofittest.R
import com.behnamuix.retrofittest.SpyGame.db.KeyWordEntity
import com.behnamuix.retrofittest.SpyGame.model.SpyModel
import com.behnamuix.retrofittest.SpyGame.model.agentEducModelList
import com.behnamuix.retrofittest.SpyGame.repository.SongController
import com.behnamuix.retrofittest.SpyGame.viewModel.ConfigGameTurnViewModel
import com.behnamuix.retrofittest.SpyGame.viewModel.ConfigRoleViewModel
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import com.behnamuix.retrofittest.ui.theme.traffic
import kotlinx.coroutines.launch


@Composable
fun ConfigGameSc(
    navController: NavController,
    gameViewModel: GameViewModel,
    configRoleViewModel: ConfigRoleViewModel,
    configGameViewModel: ConfigGameTurnViewModel
) {

    //context
    val ctx = LocalContext.current

    //vm
    var page by remember { mutableStateOf("home") }
    val showAddWordDialog = remember { mutableStateOf(false) }
    var userUse by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    val listWord by configGameViewModel.wordList.collectAsState()

    val transition = updateTransition(expanded)
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
    LaunchedEffect(Unit) {
        configGameViewModel.getWords()
        SongController.play()
    }
    Column(Modifier.fillMaxSize()) {
            "training" -> {
                Training(setPage = { page = it }, configGameViewModel)
            }

            "home" -> {
                Box(Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                expanded = !expanded
                            }) {
                            Icon(
                                tint = Color.White,
                                painter = painterResource(R.drawable.icon_setting),
                                modifier = Modifier
                                    .size(32.dp)

                                    .rotate(rotateIcon),
                                contentDescription = ""
                            )
                        }
                        AnimatedVisibility(
                            expanded, enter = fadeIn(), exit = fadeOut()
                        ) {
                            OutlinedCard(Modifier.padding(16.dp)) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "میخوای بازی از کلمات تو استفاده کنه",
                                        Modifier.weight(0.8f)
                                    )
                                    Checkbox(


                                        modifier = Modifier.weight(0.2f),
                                        checked = userUse,
                                        onCheckedChange = {
                                            userUse = it
                                            if (it) {
                                                if (configGameViewModel.checkDb()) {
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
                        Text(
                            "تنظیمات بازی",
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        AgentComp(
                            title = "تعداد ماموران",
                            agentCount = configGameViewModel.agentCount,
                            configGameViewModel,
                        )
                        SpyComp(
                            "تعداد جاسوسان",
                            configGameViewModel.spyCount,
                            configGameViewModel
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.White.copy(0.5f),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        if (listWord.isNotEmpty()) {
                            progress = false
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
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row {
                                //training
                                OutlinedButton(
                                    border = BorderStroke(1.dp, Color(0xFF03A9F4)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier,

                                    onClick = { page = "training" }
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
                                    onClick = { showAddWordDialog.value = true }
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

                                configGameViewModel.configRole()
                                page = "role"

                            }
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = " برو بعدی (${configRoleViewModel.category.size.toString()} کلمه)",
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

                        if (showAddWordDialog.value) {
                            AddKeyWordAlert(
                                showAddWordDialog,
                                configGameViewModel,
                                ctx,
                                listWord

                            )
                        }
                    }


                }
            }

            "role" -> {
        RoleManagerSc(
            userUse,
            listPlayer = configGameViewModel.playerList,
            configRoleViewModel,
            gameViewModel,

            )
            }

    }

}

@Composable
fun Training(setPage: (String) -> Unit, configGameViewModel: ConfigGameTurnViewModel) {

    val scope = rememberCoroutineScope()
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { configGameViewModel.listRole.size })
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    setPage("home")
                }
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    tint = Color.White,
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = ""
                )

            }

            Text(
                "آموزش نقش ها",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),

                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall
            )


        }

        TabRow(

            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,

            ) {
            configGameViewModel.listRole.forEachIndexed { index, item ->
                Tab(

                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(item, fontFamily = traffic, fontSize = 16.sp) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.3f)
                )
            }
        }
        HorizontalPager(

            modifier = Modifier.weight(1f),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(22.dp)
                    ) {

                        Image(
                            modifier = Modifier
                                .size(250.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(R.drawable.img_agent),
                            contentDescription = ""
                        )


                        agentEducModelList.forEach {
                            AgentRoleComp(it.title, desc = it.desc)
                        }
                    }


                }

                1 -> {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(22.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(250.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(R.drawable.img_spy),
                            contentDescription = ""
                        )
                        SpyRoleComp("اطلاعات", desc = SpyModel().etelaat)
                        SpyRoleComp("هدف", desc = SpyModel().hadaf)
                        Row {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "منطق و استراتژی",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.width(8.dp))

                            Icon(

                                tint = Color(0xFF4CAF50),
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = ""
                            )

                        }
                        SpyRoleComp(
                            ">  پنهان کاری",
                            desc = SpyModel().penhankari,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  تقلید",
                            desc = SpyModel().taghlid,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  گوش دادن فعال",
                            desc = SpyModel().gooshdadanFaal,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  گمراه کردن",
                            desc = SpyModel().gomrahKardan,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  حدس زدن",
                            desc = SpyModel().hadsZadan,
                            space = 16,
                            showIcon = false
                        )

                        SpyRoleComp("چالش ", desc = SpyModel().chalesh)
                    }
                }

            }


        }
    }


}

@Composable
fun AgentRoleComp(title: String, desc: String) {
    var rotateState by remember { mutableStateOf(0f) }
    val animateRotate by animateFloatAsState(
        targetValue = rotateState,
        animationSpec = tween(1500, easing = EaseInElastic)

    )
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable { rotateState = 360f },
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))

            Icon(
                modifier = Modifier.rotate(animateRotate),
                tint = Color(0xFF4CAF50),
                imageVector = Icons.Default.CheckCircle,
                contentDescription = ""
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = desc,
            fontSize = 18.sp,
            color = Color.White.copy(0.5f)
        )
    }
}

@Composable
fun SpyRoleComp(
    title: String,
    desc: String,
    space: Int = 0,
    showIcon: Boolean = true
) {
    var rotateState by remember { mutableStateOf(0f) }
    val animateRotate by animateFloatAsState(
        targetValue = rotateState,
        animationSpec = tween(2000, easing = EaseInElastic)

    )
    if (showIcon) {
        Column(modifier = Modifier.padding(end = space.dp)) {
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { rotateState = 360f },
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))

                Icon(
                    modifier = Modifier.rotate(animateRotate),
                    tint = Color(0xFF4CAF50),
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = ""
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = desc,
                fontSize = 18.sp,
                color = Color.White.copy(0.5f)
            )
        }
    } else {
        Column(modifier = Modifier.padding(end = space.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = desc,
                fontSize = 16.sp,
                color = Color.White.copy(0.5f)
            )

        }
    }
}

@Composable
fun ListKeyWordsComp(
    vm: ConfigGameTurnViewModel,
    listWord: MutableList<KeyWordEntity>,
) {
    var count by remember { mutableIntStateOf(-1) }
    val ctx = LocalContext.current

    Column(Modifier.padding(8.dp)) {
        //list word
        if (listWord.isEmpty()) {

            Text(

                fontFamily = traffic,
                fontSize = 14.sp,
                text = "پایگاه داده کلماتت خالیه!",
                color = Color.Black.copy(0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
            ) {
                itemsIndexed(listWord) { index, item ->
                    OutlinedCard(
                        border = BorderStroke(
                            1.dp, color = if (count == index) {
                                Color.White.copy(0.3f)
                            } else {
                                Color.Transparent
                            }
                        ),
                        onClick = { count = index },
                        elevation = CardDefaults.elevatedCardElevation(6.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            IconButton({
                                try {
                                    vm.deleteWord(
                                        id = item.id
                                    )
                                    listWord.removeAt(index)
                                } catch (e: IndexOutOfBoundsException) {
                                    Toast.makeText(
                                        ctx,
                                        "مشکلی موقتی در زمان حذف کلمه پیش آمد!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                vm.getWords()
                                Toast.makeText(
                                    ctx,
                                    " کلمه ${item.word} از کلمات بازی حذف شد ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(
                                    tint = Color(0xFFEF5350),
                                    modifier = Modifier.size(22.dp),
                                    painter = painterResource(R.drawable.icon_remove),
                                    contentDescription = ""
                                )

                            }

                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                text = item.word,


                                )
                        }


                    }
                }
            }
            Log.d("LOG", listWord.toString())

        }
    }


}

@Composable
fun AddKeyWordAlert(
    showAddWordDialog: MutableState<Boolean>,
    configGameViewModel: ConfigGameTurnViewModel,
    ctx: Context,
    listWord: MutableList<KeyWordEntity>,
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
                                            word = word
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

@Composable
fun InformationComp() {
    val infiniteTrans = rememberInfiniteTransition()
    val translateY by infiniteTrans.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )
    var ctx = LocalContext.current
    Box(
        Modifier


            .alpha(0.6f)
            .fillMaxWidth()
            .offset(x = 0.dp, y = translateY.dp)
            .padding(16.dp), contentAlignment = Alignment.TopCenter
    ) {
        OutlinedCard(
            modifier =
                Modifier
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("طراحی و برنامه نویسی:BehnamUix")
                Text("نسخه آلفا 3")
                OutlinedButton(
                    border = BorderStroke(
                        1.dp, color = Color(0xFFFFFF00),
                    ),
                    shape = RoundedCornerShape(8.dp), onClick = {
                        val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                            Intent.setData = "https://t.me/behnamUix".toUri()
                            Intent.setPackage = "org.telegram.messenger"
                        }
                        try {
                            ctx.startActivity(telegramIntent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(ctx, "تلگرام نصب نیست!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                    Text(
                        text = "پشتیبانی",
                        color = Color(0xFFFFFF00),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }


    }
}


@Composable
fun AgentComp(
    title: String,
    agentCount: MutableIntState,
    configGameViewModel: ConfigGameTurnViewModel,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AgentNumberCounterComp(
                    modifier = Modifier,
                    agentCount,
                    configGameViewModel

                )
                Spacer(modifier = Modifier.weight(1f))
                Text(title, style = MaterialTheme.typography.titleLarge)
            }
        }


    }


}

@Composable
fun SpyComp(
    title: String,
    spyCount: MutableIntState,
    configGameViewModel: ConfigGameTurnViewModel,
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)

            .fillMaxWidth(),
    ) {
        Row(
            Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SpyNumberCounterComp(
                    spyCount,
                    configGameViewModel
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            }
        }
    }


}

@Composable
fun AgentNumberCounterComp(
    modifier: Modifier,
    agentCount: MutableState<Int>,
    configGameViewModel: ConfigGameTurnViewModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {

            configGameViewModel.decAgentCountPlayer()

        }) {
            Card {
                Icon(
                    tint = Color.White,

                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = ""
                )
            }

        }
        Text(agentCount.value.toString(), style = MaterialTheme.typography.titleLarge)
        IconButton({


            configGameViewModel.incAgentCountPlayer()
        }) {
            Card {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = ""
                )
            }

        }
    }
}

@Composable
fun SpyNumberCounterComp(
    spyCount: MutableState<Int>,
    configGameViewModel: ConfigGameTurnViewModel,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {


            configGameViewModel.decSpyCountPlayer()


        }) {
            Card {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = ""
                )
            }

        }
        Text(spyCount.value.toString(), style = MaterialTheme.typography.titleLarge)
        IconButton({
            configGameViewModel.incSpyCountPlayer()


        }) {
            Card {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = ""
                )
            }

        }
    }
}






