package com.behnamuix.spygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.behnamuix.spygame.core.media.presentation.screen.TestMediaScreen
import com.behnamuix.spygame.ui.navigation.BottomNavigationBar
import com.behnamuix.spygame.ui.theme.SpyTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()


            SpyTheme(darkTheme = true) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
//                        TestSc()
                        // SetupNavGraph(innerPadding, navController)
                        TestMediaScreen()
                    }
                }
            }
        }
    }


}