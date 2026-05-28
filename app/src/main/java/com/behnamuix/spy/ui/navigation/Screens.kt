package com.behnamuix.spy.ui.navigation

sealed class Screens(val route:String){
    object Splash: Screens("splash")
    object ConfigGame: Screens("configGame")
    object Training: Screens("training")
    object RoleManager: Screens("roleManagement")
    object Game: Screens("game")
}