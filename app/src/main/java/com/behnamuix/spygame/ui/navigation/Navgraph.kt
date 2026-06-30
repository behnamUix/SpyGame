package com.behnamuix.spygame.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.behnamuix.spygame.ui.navigation.screens.SplashSc
import com.behnamuix.spygame.ui.navigation.screens.configGame.ConfigGameSc
import com.behnamuix.spygame.ui.navigation.screens.game.GameSc
import com.behnamuix.spygame.ui.navigation.screens.mapGame.MapGameSc
import com.behnamuix.spygame.ui.navigation.screens.otp.LoginWithOtpSc
import com.behnamuix.spygame.ui.navigation.screens.otp.OtpVerificationSc
import com.behnamuix.spygame.ui.navigation.screens.roleManager.RoleManagerSc
import com.behnamuix.spygame.ui.navigation.screens.training.TrainingSc

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SetupNavGraph(paddingValue: PaddingValues, navController: NavHostController) {
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

        composable(Screens.Map.route) {
            MapGameSc(navController = navController)
        }
    }

}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    val hiddenRoutes = listOf(
        Screens.ConfigGame.route,
        Screens.Map.route,
    )

    if (currentRoute in hiddenRoutes){
        NavigationBar(
            containerColor = Color.Black,
            modifier = modifier
                .padding(horizontal = 2.dp)
                .offset(y = 2.dp)
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Gray,
                            Color.Transparent,
                            MaterialTheme.colorScheme.primary.copy(0.4f)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            BottomNavScreen.btnNavItems.forEach { screen ->
                CustomNavigationBarItem(
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            modifier = Modifier.size(32.dp),
                            contentDescription = screen.title
                        )
                    },
                    label = {
                        Text(screen.title)
                    }
                )
            }
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val transition = updateTransition(
        targetState = selected,
        label = "IndicatorTransition"
    )

    val offsetY by transition.animateDp(
        transitionSpec = { tween(800) },
        label = "OffsetY"
    ) { isSelected ->
        if (isSelected) 0.dp else 12.dp
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(300) },
        label = "Alpha"
    ) { isSelected ->
        if (isSelected) 1f else 0f
    }

    val scale by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        },
        label = "Scale"
    ) { isSelected ->
        if (isSelected) 1f else 0.8f
    }

    val floatingBox by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "FloatingOffset"
    )


    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CompositionLocalProvider(
            LocalContentColor provides if (selected)
                MaterialTheme.colorScheme.primary
            else
                Color.Gray
        ) {
            Box(contentAlignment = Alignment.Center) {
                icon()
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .offset(y = offsetY + (if (selected) floatingBox.dp else 0.dp))
                .graphicsLayer {
                    this.alpha = alpha
                    this.scaleX = scale
                    this.scaleY = scale
                }
                .size(6.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    CircleShape
                )
        )
    }
}
