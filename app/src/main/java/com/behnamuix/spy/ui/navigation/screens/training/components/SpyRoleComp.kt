package com.behnamuix.spy.ui.navigation.screens.training.components

import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.spy.viewModel.ConfigGameViewModel

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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier

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
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                text = desc,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(0.5f)
            )
        }
    } else {
        Column(modifier = Modifier.padding(end = space.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.End,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                text = desc,
                fontSize = 16.sp,
                color = Color.White.copy(0.5f)
            )

        }
    }
}

@Composable
fun SpyComp(
    title: String,
    spyCount: Int,
    configGameViewModel: ConfigGameViewModel,
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
fun SpyNumberCounterComp(
    spyCount: Int,
    configGameViewModel: ConfigGameViewModel,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
        Text(spyCount.toString(), style = MaterialTheme.typography.titleLarge)
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