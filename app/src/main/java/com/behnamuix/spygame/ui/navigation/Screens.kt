package com.behnamuix.spygame.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.behnamuix.spygame.R

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object OtpLogin : Screens("otpLogin")
    object OtpVerification : Screens("otpVerification/{code}/{phone}") {
        fun createRoute(code: String, phone: String) = "otpVerification/$code/$phone"
    }

    object ConfigGame : Screens("configGame")
    object Training : Screens("training")
    object RoleManager : Screens("roleManagement")
    object Game : Screens("game/{time}/{word}") {
        fun createRoute(time: Int, word: String) = "game/$time/$word"

    }
}

sealed class BottomNavScreen(val route: String, val title: String, val icon: Int) {
    object HomeSc : BottomNavScreen(route = "game", title = "بازی", R.drawable.icon_game)
    object MapSc : BottomNavScreen("map", "همبازی", R.drawable.icon_map)
    companion object {
        val btnNavItems = listOf(MapSc,HomeSc )
    }
}