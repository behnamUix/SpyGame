package com.behnamuix.spy.ui.navigation.screens.game.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun QCardComp(quiz: String, answer: String) {
    var showAnswer by remember { mutableStateOf(false) }
    val transition = updateTransition(showAnswer)
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

    Column(
        modifier = Modifier.clickable(
        onClick = {
            showAnswer = !showAnswer


        }
    )) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(
                        rotateIcon
                    ),
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = ""
            )
            Spacer(Modifier.weight(1f))

            Text(
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                text = quiz,
                textAlign = TextAlign.Start,


                )
        }
        AnimatedVisibility(showAnswer) {
            HorizontalDivider(Modifier.fillMaxWidth())
            Text(
                color = Color.Black,

                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
                text = answer,
                textAlign = TextAlign.Start,


                )

        }
    }


}