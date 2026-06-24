package com.behnamuix.spygame.ui.navigation

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