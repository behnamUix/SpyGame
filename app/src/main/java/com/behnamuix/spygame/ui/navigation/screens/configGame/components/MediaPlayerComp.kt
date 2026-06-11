package com.behnamuix.spygame.ui.navigation.screens.configGame.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.behnamuix.spygame.viewModel.ConfigGameViewModel
import com.behnamuix.spygame.viewModel.MediaState

@Composable
fun MediaControllerComp(mediaState: MediaState, vm: ConfigGameViewModel) {
    var state by remember { mutableStateOf(false) }
    val transition = updateTransition(state, label = "")
    val rotate by transition.animateFloat(transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        )
    }) {
        if (it) {
            360f
        } else {
            0f
        }
    }
    when (mediaState) {
                MediaState . PLAY -> {
            IconButton({ vm.pause();state = !state }) {
                Icon(
                    Icons.Default.Pause,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotate)
                )
            }
        }

        MediaState.PAUSE -> {
            IconButton({ vm.play();state = !state }) {
                Icon(

                    Icons.Default.PlayArrow,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotate)

                )
            }
        }

        else -> {}
    }
    Icon(
        Icons.Default.MusicNote,
        contentDescription = ""
    )
}