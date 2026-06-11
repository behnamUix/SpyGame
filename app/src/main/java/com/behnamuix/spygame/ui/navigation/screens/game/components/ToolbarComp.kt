package com.behnamuix.spygame.ui.navigation.screens.game.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ToolbarComp(title: String, word: String = "") {
    val ctx = LocalContext.current
    Text(
        color = Color.White,
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable {
                Toast.makeText(ctx, word, Toast.LENGTH_SHORT).show()
            },
        text = title,
        style = MaterialTheme.typography.headlineSmall
    )
}

