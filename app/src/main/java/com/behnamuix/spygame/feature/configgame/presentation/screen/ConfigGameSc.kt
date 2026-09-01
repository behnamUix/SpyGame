package com.behnamuix.spygame.feature.configgame.presentation.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.behnamuix.appointment.const.BACKGROUND_URL
import com.behnamuix.spygame.R
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.feature.configgame.presentation.contract.ConfigGameContract
import com.behnamuix.spygame.feature.configgame.presentation.viewmodel.ConfigGameViewModel
import com.behnamuix.spygame.ui.theme.AppDimens
import com.behnamuix.spygame.ui.theme.AppShapes
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


@Composable
fun ConfigGameSc(
    navController: NavController,
    vm: ConfigGameViewModel = koinViewModel(),
    dsVm: DataStoreViewModel = koinViewModel(),
) {


    ConfigGameContent(vm)
}

@Composable
fun ConfigGameContent(vm: ConfigGameViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBE9A73))
    ) {

        val configGameState = vm.configGameState
            .collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {

            vm.onAction(ConfigGameContract.ConfigGameAction.setBiometricProgress)


        }

        AsyncImage(
            model = BACKGROUND_URL,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding)
        ) {

            Spacer(
                modifier = Modifier.height(
                    AppDimens.contentTopSpace
                )
            )

            Text(
                text = "CONFIDENTIAL INFORMATION",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(
                    AppDimens.contentTopSpace
                )
            )

            CountCard(
                title = "AGENT COUNT",
                count = configGameState.value.agentCount,
                onDecrease = {
                    vm.onAction(
                        ConfigGameContract.ConfigGameAction
                            .DecreaseAgentCount
                    )
                },
                onIncrease = {
                    vm.onAction(
                        ConfigGameContract.ConfigGameAction
                            .IncreaseAgentCount
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    AppDimens.screenPadding
                )
            )

            CountCard(
                title = "SPY COUNT",
                count = configGameState.value.spyCount,
                iconTint = MaterialTheme.colorScheme.error,
                onDecrease = {
                    vm.onAction(
                        ConfigGameContract.ConfigGameAction
                            .DecreaseSpyCount
                    )
                },
                onIncrease = {
                    vm.onAction(
                        ConfigGameContract.ConfigGameAction
                            .IncreaseSpyCount
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(
                    AppDimens.screenPadding
                )
            )
            AiCard(configGameState)
        }
    }

}


@Composable
fun CountCard(
    title: String,
    count: Int,
    iconTint: Color = Color.Unspecified,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = AppDimens.borderWidth,
                color = Color.Black,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.screenPadding)
        ) {

            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                modifier = Modifier.padding(
                    top = AppDimens.dividerTopPadding
                )
            )

            Row(
                modifier = Modifier.padding(
                    top = AppDimens.dividerTopPadding
                )
            ) {

                Icon(
                    painter = painterResource(R.drawable.icon_spy),
                    tint = iconTint,
                    contentDescription = null,
                    modifier = Modifier
                        .size(AppDimens.iconHero)
                        .border(
                            width = AppDimens.borderWidth,
                            color = Color.Black,
                            shape = MaterialTheme.shapes.large
                        )
                )

                Column(
                    modifier = Modifier.padding(
                        start = AppDimens.screenPadding
                    )
                ) {
                    Row {
                        Text(
                            text = "Code:",
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "24G7",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Column {
                        Text(
                            text = "Status:",
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Infiltered",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(
                        start = AppDimens.screenPadding
                    )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            AppDimens.spacingMedium
                        )
                    ) {

                        Box(
                            modifier = Modifier
                                .size(AppDimens.spacingXl)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = AppShapes.small
                                )
                                .clickable {
                                    onDecrease()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                tint = Color.Black,
                                modifier = Modifier.size(AppDimens.iconMedium),
                                painter = painterResource(
                                    R.drawable.icon_minus
                                ),
                                contentDescription = "Decrease count"
                            )
                        }

                        Text(
                            color = Color.Black,
                            text = count.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Box(
                            modifier = Modifier
                                .size(AppDimens.spacingXl)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = AppShapes.small
                                )
                                .clickable {
                                    onIncrease()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                tint = Color.Black,
                                modifier = Modifier.size(AppDimens.iconMedium),
                                painter = painterResource(
                                    R.drawable.icon_plus
                                ),
                                contentDescription = "Increase count"
                            )
                        }
                    }

                    Text(text = "9587295729857123", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun AiCard(configGameState: State<ConfigGameContract.ConfigGameState>) {
    val animatedProgress by animateFloatAsState(
        targetValue = configGameState.value.biometricSyncProg,
        animationSpec = tween(
            durationMillis = 800
        ),
        label = "progress"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = AppDimens.borderWidth,
                color = Color.Black,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.screenPadding)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.fingerprint),
                    contentDescription = "", modifier = Modifier.size(70.dp)
                )
                Image(
                    painterResource(R.drawable.handprint),
                    contentDescription = "", modifier = Modifier.size(70.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(

                        text = "BIOMETRIC SYNC:${(configGameState.value.biometricSyncProg * 100).toInt()}%",
                        letterSpacing = TextUnit(0.4f, TextUnitType.Sp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )


                    LinearProgressIndicator(

                        progress = { animatedProgress },
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RectangleShape)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp)),
                        color = Color.Black,
                        trackColor = Color.Transparent,

                        strokeCap = StrokeCap.Butt,
                    )
                    Text(

                        text = "DATA VERIFIED",
                        letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                    Text(


                        text = "DATA VERIFIED",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

