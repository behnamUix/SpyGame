package com.behnamuix.spygame

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.behnamuix.spygame.media.viewmodel.MediaPlayerViewModel
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
            val backgroundList = listOf(
                R.drawable.bg1,
                R.drawable.bg2, R.drawable.bg3,
                R.drawable.bg4, R.drawable.bg5,
                R.drawable.bg6, R.drawable.bg7,
                R.drawable.bg8, R.drawable.bg9,
                R.drawable.bg10, R.drawable.bg11,
                R.drawable.bg12, R.drawable.bg13,
                R.drawable.bg14,R.drawable.bg15,
                R.drawable.bg16
            )


            var selectedIndex by remember { mutableIntStateOf(1) }
            val listState = rememberLazyListState()
            val navController = rememberNavController()

            // Scroll to the selected index whenever it changes
            LaunchedEffect(selectedIndex) {
                listState.animateScrollToItem(selectedIndex)
            }

            SpyTheme(darkTheme = true) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {

                        Image(
                            painter = painterResource(id = backgroundList[selectedIndex]),
                            contentDescription = "Background",
                            modifier = Modifier.fillMaxSize().blur(2.dp),
                            contentScale = ContentScale.FillBounds
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y=-85.dp)

                                .padding(8.dp),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(onClick = {
                                // رفتن به عکس قبلی
                                selectedIndex = if (selectedIndex > 0) {
                                    selectedIndex - 1
                                } else {
                                    backgroundList.size - 1
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Previous Background",
                                    tint = androidx.compose.ui.graphics.Color.White
                                )
                            }
                            LazyRow(
                                state = listState,
                                modifier = Modifier.fillMaxWidth(0.3f)
                            ) {
                                itemsIndexed(backgroundList){index,_ ->
                                    RadioButton(
                                        selected = index == selectedIndex,
                                        onClick = {
                                            selectedIndex = index
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            IconButton(onClick = {
                                // رفتن به عکس بعدی
                                selectedIndex = (selectedIndex + 1) % backgroundList.size
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Next Background",
                                    tint = androidx.compose.ui.graphics.Color.White
                                )
                            }
                        }
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