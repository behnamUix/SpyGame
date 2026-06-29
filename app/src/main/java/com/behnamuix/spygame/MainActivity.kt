package com.behnamuix.spygame

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.behnamuix.spygame.media.config.getMediaPlayer
import com.behnamuix.spygame.media.repo.MediaPlayerRepository
import com.behnamuix.spygame.ui.navigation.BottomNavigationBar
import com.behnamuix.spygame.ui.navigation.SetupNavGraph
import com.behnamuix.spygame.ui.theme.SpyTheme
import com.behnamuix.spygame.media.viewmodel.MediaPlayerViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val mediaVm: MediaPlayerViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaVm.play()
        setContent {
            val navController = rememberNavController()
            SpyTheme(darkTheme = true) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController=navController) },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    @SuppressLint("NewApi")
                    Box() {
                        Image(
                            painter = painterResource(R.drawable.spy_bg),
                            contentDescription = ""
                        )
                        SetupNavGraph(innerPadding, navController)

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mediaVm.isPlaying()) {
            mediaVm.play()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaVm.stop()
        mediaVm.release()


    }

    override fun onPause() {
        super.onPause()
        mediaVm.pause()
        Toast.makeText(this, "بازی در حال اجراست!", Toast.LENGTH_SHORT).show()

    }

}

