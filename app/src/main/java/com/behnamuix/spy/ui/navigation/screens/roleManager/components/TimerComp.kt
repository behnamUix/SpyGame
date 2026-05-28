package com.behnamuix.spy.ui.navigation.screens.roleManager.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import com.behnamuix.retrofittest.SpyGame.viewModel.RoleManagerViewModel

@Composable
fun TimerCard(
    title: String,
    vm: RoleManagerViewModel,
    time: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            Color.Transparent
        ),
        enabled = true,
        onClick = {},
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),

            ) {
            Row(

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                TimerComp(
                    modifier = Modifier,
                    time,
                    vm
                )
                Spacer(Modifier.weight(1f))

                Text(title, modifier = Modifier.padding(end = 20.dp))
            }
        }


    }
}

@Composable
fun TimerComp(
    modifier: Modifier,
    time: Int,
    vm: RoleManagerViewModel
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            vm.decTime()
        }) {
            Text("—", fontSize = 20.sp)
        }
        Text(time.toString())
        IconButton({
            vm.incTime()
        }) {

            Icon(

                imageVector = Icons.Default.Add, contentDescription = ""
            )
        }
    }
}