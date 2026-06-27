package com.behnamuix.spygame.ui.navigation.screens.mapGame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MapGameSc(modifier: Modifier = Modifier, navController: NavController) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column {

        Text("... به زودی ", style = MaterialTheme.typography.headlineSmall)

        }
    }

}