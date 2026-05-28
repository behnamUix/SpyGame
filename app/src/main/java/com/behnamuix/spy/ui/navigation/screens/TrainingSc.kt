package com.behnamuix.spy.ui.navigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.behnamuix.retrofittest.SpyGame.model.Spy
import com.behnamuix.spy.R
import com.behnamuix.spy.ui.navigation.Screens
import com.behnamuix.spy.viewModel.TrainingViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingSc(navController: NavController, trainingVm: TrainingViewModel = koinViewModel()) {

    val scope = rememberCoroutineScope()
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { trainingVm.listRole.size })
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
                    navController.navigate(Screens.ConfigGame.route)
                }
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    tint = Color.White,
                    imageVector = Icons.Default.ArrowBackIosNew,
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
            trainingVm.listRole.forEachIndexed { index, item ->
                Tab(

                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(item, style = MaterialTheme.typography.titleMedium) },
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


                        trainingVm.agentEducationList.forEach {
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
                        SpyRoleComp("اطلاعات", desc = Spy().etelaat)
                        SpyRoleComp("هدف", desc = Spy().hadaf)
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
                            desc = Spy().penhankari,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  تقلید",
                            desc = Spy().taghlid,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  گوش دادن فعال",
                            desc = Spy().gooshdadanFaal,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  گمراه کردن",
                            desc = Spy().gomrahKardan,
                            space = 16,
                            showIcon = false
                        )
                        SpyRoleComp(
                            ">  حدس زدن",
                            desc = Spy().hadsZadan,
                            space = 16,
                            showIcon = false
                        )

                        SpyRoleComp("چالش ", desc = Spy().chalesh)
                    }
                }

            }


        }
    }


}