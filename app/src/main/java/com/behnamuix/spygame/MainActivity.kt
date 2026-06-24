package com.behnamuix.spygame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.behnamuix.spygame.ui.navigation.AppNavgraph
import com.behnamuix.spygame.ui.theme.SpyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpyTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    @SuppressLint("NewApi")
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Image(
                            painter = painterResource(R.drawable.spy_bg),
                            contentDescription = ""
                        )
                        AppNavgraph()

                    }
                }
            }
        }
    }
}

