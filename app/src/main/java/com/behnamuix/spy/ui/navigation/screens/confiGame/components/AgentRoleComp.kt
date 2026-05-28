package com.behnamuix.spy.ui.navigation.screens.confiGame.components

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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.spy.viewModel.ConfigGameViewModel

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
fun AgentComp(
    title: String,
    agentCount: MutableIntState,
    configGameViewModel: ConfigGameViewModel,
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
fun AgentNumberCounterComp(
    modifier: Modifier,
    agentCount: MutableState<Int>,
    configGameViewModel: ConfigGameViewModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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



