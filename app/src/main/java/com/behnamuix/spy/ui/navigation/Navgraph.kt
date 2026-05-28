package com.behnamuix.spy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.behnamuix.spy.ui.navigation.screens.ConfigGameSc
import com.behnamuix.spy.ui.navigation.screens.GameSc
import com.behnamuix.spy.ui.navigation.screens.RoleManagerSc
import com.behnamuix.spy.ui.navigation.screens.SplashSc
import com.behnamuix.spy.ui.navigation.screens.TrainingSc

@Composable
fun AppNavgraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) { SplashSc(navController=navController) }
        composable(Screens.ConfigGame.route) { ConfigGameSc(navController=navController) }
        composable(Screens.Training.route) { TrainingSc(navController=navController) }
        composable(Screens.RoleManager.route) { RoleManagerSc(navController=navController) }
        composable(Screens.Game.route) { GameSc(navController=navController) }
    }

}