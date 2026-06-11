package com.behnamuix.spygame.ui.navigation.screens.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.behnamuix.spygame.R
import com.behnamuix.spygame.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spygame.ui.navigation.Screens

@Composable
fun AlertCardComp(
    mediaVm: MediaPlayerViewModel,
    navController: NavController,
    word: String,
    onDismiss: () -> Unit = {}
) {
    mediaVm.stop()

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.img_spy),
                    contentDescription = "Spy",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    tint = Color.Unspecified
                )

                Text(
                    text = " جاسوس ‌ها برنده شدند!️",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5252),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White.copy(0.3f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = "کلمه بازی: $word",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "زمان تمام شد و مامورها نتوانستند جاسوس را پیدا کنند",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(0.6f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate(Screens.ConfigGame.route) {
                            popUpTo(Screens.ConfigGame.route) {
                                inclusive = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "بازی دوباره",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}