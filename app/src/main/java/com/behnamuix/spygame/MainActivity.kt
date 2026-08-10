package com.behnamuix.spygame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.behnamuix.spygame.core.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spygame.feature.configgame.presentation.screen.TestSc
import com.behnamuix.spygame.ui.navigation.BottomNavigationBar
import com.behnamuix.spygame.ui.navigation.SetupNavGraph
import com.behnamuix.spygame.ui.theme.SpyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mediaVm: MediaPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaVm.play()

        setContent {

            val navController = rememberNavController()


            SpyTheme(darkTheme = true) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        TestSc()
                       // SetupNavGraph(innerPadding, navController)
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

    override fun onPause() {
        super.onPause()
        mediaVm.pause()
        // پیام مناسب‌تر
        Toast.makeText(this, "موسیقی متوقف شد", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaVm.stop()
        mediaVm.release()
    }
}