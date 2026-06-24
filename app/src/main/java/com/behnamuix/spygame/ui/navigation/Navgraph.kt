package com.behnamuix.spygame.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.behnamuix.spygame.ui.navigation.screens.SplashSc
import com.behnamuix.spygame.ui.navigation.screens.configGame.ConfigGameSc
import com.behnamuix.spygame.ui.navigation.screens.game.GameSc
import com.behnamuix.spygame.ui.navigation.screens.otp.LoginWithOtpSc
import com.behnamuix.spygame.ui.navigation.screens.otp.OtpVerificationSc
import com.behnamuix.spygame.ui.navigation.screens.roleManager.RoleManagerSc
import com.behnamuix.spygame.ui.navigation.screens.training.TrainingSc

@Composable
fun AppNavgraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.ConfigGame.route) {
        composable(Screens.Splash.route) { SplashSc(navController = navController) }
        composable(Screens.OtpLogin.route) { LoginWithOtpSc(navController = navController) }
        composable(Screens.OtpVerification.route) {
            val code = navController.currentBackStackEntry?.arguments?.getString("code")
            val phone = navController.currentBackStackEntry?.arguments?.getString("phone")
            OtpVerificationSc(navController = navController, code = code ?: "", phone = phone ?: "")
        }
        composable(Screens.ConfigGame.route) { ConfigGameSc(navController = navController) }
        composable(Screens.Training.route) { TrainingSc(navController = navController) }
        composable(Screens.RoleManager.route) {
            RoleManagerSc { time, word ->
                navController.navigate(Screens.Game.createRoute(time, word))
            }
        }
        composable(
            Screens.Game.route,
            arguments = listOf(
                navArgument("time") { type = NavType.IntType },
                navArgument("word") { type = NavType.StringType },
            )
        ) {
            val word = navController.currentBackStackEntry?.arguments?.getString("word")
            val time = navController.currentBackStackEntry?.arguments?.getInt("time")
            GameSc(navController = navController, time = time ?: 0, word = word ?: "null")
        }
    }

}