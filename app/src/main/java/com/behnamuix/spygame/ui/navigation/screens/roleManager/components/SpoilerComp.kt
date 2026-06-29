package com.behnamuix.spygame.ui.navigation.screens.roleManager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SpoilerComp(
    state: MutableIntState,
    index: Int,
    onRoleShown: () -> Unit
){
    Box(
        modifier = Modifier
            .clickable {
                state.intValue = index
                onRoleShown()
            }
            .padding(2.dp)
            .fillMaxSize()
            .background(
                color = Color.White, shape = RoundedCornerShape(8.dp)
            )
    ) {

        Box(
            modifier = Modifier
                .testTag("showRole")
                .clickable {
                    state.intValue = index
                    onRoleShown()
                }
                .padding(32.dp)
                .fillMaxSize()
                .background(
                    color = Color.White, shape = RoundedCornerShape(8.dp)

                )


        ) {
            Box(Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(
                    " رو اینجا ضربه بزن تا نقشت رو نشون بدم",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


}