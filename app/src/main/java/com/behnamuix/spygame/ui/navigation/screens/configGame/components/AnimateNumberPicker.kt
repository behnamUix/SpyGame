package com.behnamuix.spygame.ui.navigation.screens.configGame.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpInputComp(
    count: String,
    setCount: (String) -> Unit,
    list: List<Any>,
    error: Boolean = false,
    width: Int = 64,
    height: Int = 72,
    numberColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
    selectedNumberColor: Color = MaterialTheme.colorScheme.primary,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest
) {
    val listNumber = list
    val internalIndex = count.toIntOrNull() ?: 0
    var stateTop by remember { mutableStateOf(false) }
    var stateDown by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = internalIndex)
    val scope = rememberCoroutineScope()
    val transitionErrorColor = updateTransition(error, label = "Error")
    val errorColor by transitionErrorColor.animateColor(label = "Color") {
        if (it) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant
    }
    val transitionTop = updateTransition(stateTop, label = "Top")
    val transitionDown = updateTransition(stateDown, label = "Down")
    val rotation by transitionTop.animateFloat(label = "Rotation") {
        if (it) -8f else 0f
    }
    val transformTop by transitionTop.animateDp(label = "TopDp") {
        if (it) 12.dp else 0.dp
    }
    val transformDown by transitionDown.animateDp(label = "DownDp") {
        if (it) 12.dp else 0.dp
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .offset(y = -transformTop),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = {
                scope.launch {
                    if (internalIndex > 0) {
                        stateTop = true
                        val newIndex = internalIndex - 1
                        setCount(listNumber[newIndex].toString())
                        scrollState.animateScrollToItem(newIndex)
                        delay(300)
                        stateTop = false
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "افزایش عدد"
            )
        }


        Card(
            border = BorderStroke(
                width = if (error) 2.dp else 1.dp,
                color = errorColor
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            modifier = Modifier
                .rotate(rotation)
                .size(width = width.dp, height = height.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    userScrollEnabled = false,
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(listNumber) { index, item ->
                        val isSelected = item.toString() == count

                        Text(
                            text = item.toString(),
                            color = if (isSelected) selectedNumberColor else numberColor,
                            style = if (isSelected) {
                                MaterialTheme.typography.headlineMedium.copy(fontSize = 26.sp)
                            } else {
                                MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                            },
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
                // ماسک گرادینت بالا و پایین (هماهنگ با تم جاری دستگاه)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    cardBackgroundColor.copy(alpha = 0.8f),
                                    Color.Transparent,
                                    Color.Transparent,
                                    cardBackgroundColor.copy(alpha = 0.8f),
                                )
                            )
                        )
                )
            }
        }
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .offset(y = transformDown),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = {
                scope.launch {
                    if (internalIndex < listNumber.size - 1) {
                        stateDown = true
                        val newIndex = internalIndex + 1
                        setCount(listNumber[newIndex].toString())
                        scrollState.animateScrollToItem(newIndex)
                        delay(300)
                        stateDown = false
                    }
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "کاهش عدد"
            )
        }
    }
}


