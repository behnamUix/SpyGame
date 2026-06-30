package com.behnamuix.spygame.ui.navigation

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

    object Map : Screens("map")
}

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object HomeSc : BottomNavScreen(Screens.ConfigGame.route, "بازی", R.drawable.icon_game)
    object MapSc : BottomNavScreen(Screens.Map.route, "همبازی", R.drawable.icon_map)

    companion object {
        val btnNavItems get() = listOf(HomeSc, MapSc)
    }
}
