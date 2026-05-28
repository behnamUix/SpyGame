package com.behnamuix.spy.ui.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.behnamuix.spy.viewModel.SplashViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashSc(
    modifier: Modifier = Modifier,
    navController: NavController,
    splashVm: SplashViewModel = koinViewModel()
) {

}