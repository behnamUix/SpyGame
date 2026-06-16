package com.behnamuix.spygame.ui.navigation.screens.configGame.components.otp

import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.utils.setLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs


@Composable
fun OtpVerification(
    dsVm: DataStoreViewModel = koinViewModel(),
    setPage: (String) -> Unit,
    setShow: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var userCode by remember { mutableStateOf("") }

    // خواندن کد ذخیره‌شده در DataStore
    var savedCode by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        dsVm.code.collect { newCode ->
            savedCode = newCode
            setLog("کد دریافت شده از DataStore: $newCode")  // لاگ بگیر ببین چی میاد
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {

        Text(
            "احزاز هویت",

            fontWeight = FontWeight.Bold
        )
        Text(
            "کد چهار رقمی که برات ارسال شده رو وارد کن",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val a = OtpInputComp(listOf(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
            val b = OtpInputComp(listOf(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
            val c = OtpInputComp(listOf(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
            val d = OtpInputComp(listOf(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
            userCode = a + b + c + d
            setLog(userCode.toString())

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (userCode == savedCode) {
                        Toast.makeText(context, "شما وارد شدید", Toast.LENGTH_SHORT).show()
                        dsVm.setLoggedInState(true)
                        setShow(false)
                        setLog(dsVm.code.toString())
                        setLog(userCode)
                    } else {
                        Toast.makeText(context, "❌ کد اشتباه است!", Toast.LENGTH_SHORT).show()
                        setLog(userCode.toString())

                    }

                }) {
                Text(
                    "ثبت نام",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    setPage("login")


                }) {
                Text(
                    "برگشت",
                    color = Color.White.copy(0.5f),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }


    }
}

@Composable
fun OtpInputComp(
    list: List<Any>,
    error: Boolean = false,
    width: Int = 70,
    height: Int = 70,
    numberColor: Color = Color.Black.copy(0.4f),
    selectedNumberColor: Color = Color.Black,
    cardBackgroundColor: Color = Color.White,
): String {
    val listNumber = list
    var count by remember { mutableStateOf(0) }
    var stateTop by remember { mutableStateOf(false) }
    var stateDown by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val transitionErrorColor = updateTransition(error)
    val transitionTop = updateTransition(stateTop)
    val transitionDown = updateTransition(stateDown)
    val errorColor by transitionErrorColor.animateColor(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) {
            Color(0xFFEF5350)
        } else {
            Color.Transparent
        }
    }
    val rotation by transitionTop.animateFloat(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) {
            -10f
        } else {
            0f
        }
    }
    val transformTop by transitionTop.animateDp(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) {
            20.dp
        } else {
            0.dp
        }
    }
    val transformDown by transitionDown.animateDp(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) {
            20.dp
        } else {
            0.dp
        }
    }
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    modifier = Modifier.offset(y = -transformTop), onClick = {

                        scope.launch {
                            if (count > 0) {
                                stateTop = true
                                delay(500)
                                stateTop = false
                                count--
                                scrollState.animateScrollToItem(abs(count))


                            }
                        }
                    }) {
                    Icon(
                        modifier = Modifier.size(55.dp),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }
                Card(
                    border = BorderStroke(
                        1.dp, color = errorColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                    colors = CardDefaults.cardColors(cardBackgroundColor),
                    modifier = Modifier
                        .rotate(rotation)
                        .size(width = width.dp, height = height.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,

                        ) {

                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            userScrollEnabled = false,

                            state = scrollState
                        ) {
                            itemsIndexed(listNumber) { index, item ->
                                Text(

                                    text = item.toString(),
                                    color = if (index - 1 == count) {
                                        selectedNumberColor
                                    } else {
                                        numberColor
                                    },
                                    fontSize = if (index - 1 == count) {
                                        24.sp
                                    } else {
                                        14.sp
                                    }
                                )
                                Spacer(Modifier.height(8.dp))

                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.LightGray,
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.LightGray,

                                            )
                                    )
                                )
                        ) {

                        }
                    }
                }
                IconButton(
                    modifier = Modifier.offset(y = transformDown), onClick = {

                        scope.launch {

                            if (count < 9) {
                                stateDown = true
                                delay(500)
                                stateDown = false
                                count++
                                scrollState.animateScrollToItem(abs(count))

                            }

                        }

                    }) {
                    Icon(
                        modifier = Modifier.size(55.dp),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }

            }

        }
    }
    return (count).toString()

}