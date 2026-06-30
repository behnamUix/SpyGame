package com.behnamuix.spygame.ui.navigation.screens.game.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.spygame.ui.navigation.Screens
import com.behnamuix.spygame.viewModel.GameViewModel

@Composable
fun BottomBarComp(navController: NavController, vm: GameViewModel, isRunning: Boolean, word: String) {
    var show= remember{ mutableStateOf(false) }
    Row(
        modifier = Modifier.animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isRunning) {
            IconButton(
                modifier = Modifier.border(
                    1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
                onClick = {
                    vm.stopTimer()
                }) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = ""
                )
            }
        } else {
            IconButton(
                modifier = Modifier.border(
                    1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
                onClick = {
                    vm.resumeTimer()
                }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = ""
                )
            }
        }
        IconButton(
            modifier = Modifier.border(
                1.dp,
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
            onClick = {
                navController.navigate(Screens.ConfigGame.route) {
                    popUpTo(Screens.ConfigGame.route) {
                        inclusive = true
                    }
                }
            }) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = ""
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier,
            onClick = {
                show.value=true
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = "مامورا برنده شدن!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
    if(show.value){
        AlertAgentWinComp(navController,word)
    }
}