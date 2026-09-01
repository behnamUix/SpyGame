package com.behnamuix.spygame.feature.configgame.presentation.screen

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.behnamuix.spygame.ui.theme.SpyTheme
import org.koin.androidx.compose.koinViewModel


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
    val configState by vm.configGameState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBE9A73))
    ) {
        var configGameState = vm.configGameState.collectAsStateWithLifecycle()
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
            Spacer(modifier = Modifier.height(AppDimens.contentTopSpace))

            Text(
                text = "CONFIDENTIAL INFORMATION",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppDimens.contentTopSpace))

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
                        text = "AGENT COUNT",
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
                        modifier = Modifier.padding(top = AppDimens.dividerTopPadding)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_spy),
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
                            modifier = Modifier.padding(start = AppDimens.screenPadding)
                        ) {
                            Text(text = "Code: 24G7")
                            Text(text = "Status:\nInfiltered")
                        }
                        Column(
                            modifier = Modifier.padding(start = AppDimens.screenPadding)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacingMedium)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(AppDimens.spacingXl)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = AppShapes.small
                                        )
                                        .clickable { vm.onAction(ConfigGameContract.ConfigGameAction.DecreaseAgentCount) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(AppDimens.iconMedium),
                                        painter = painterResource(R.drawable.icon_minus),
                                        contentDescription = "Decrease count",

                                        )
                                }

                                Text(
                                    text = configGameState.value.agentCount.toString(),
                                    style = MaterialTheme.typography.headlineMedium,

                                    )

                                Box(
                                    modifier = Modifier
                                        .size(AppDimens.spacingXl)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = AppShapes.small
                                        )
                                        .clickable { vm.onAction(ConfigGameContract.ConfigGameAction.IncreaseAgentCount) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(AppDimens.iconMedium),
                                        painter = painterResource(R.drawable.icon_plus),
                                        contentDescription = "Increase count",

                                        )
                                }
                            }
                            Text(text = "9587295729857123")
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.height(AppDimens.screenPadding))
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
                        text = "SPY COUNT",
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
                        modifier = Modifier.padding(top = AppDimens.dividerTopPadding)
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.error,
                            painter = painterResource(R.drawable.icon_spy),
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
                            modifier = Modifier.padding(start = AppDimens.screenPadding)
                        ) {
                            Text(text = "Code: 24G7")
                            Text(text = "Status:\nInfiltered")
                        }
                        Column(
                            modifier = Modifier.padding(start = AppDimens.screenPadding)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacingMedium)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(AppDimens.spacingXl)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = AppShapes.small
                                        )
                                        .clickable { vm.onAction(ConfigGameContract.ConfigGameAction.DecreaseSpyCount) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(AppDimens.iconMedium),
                                        painter = painterResource(R.drawable.icon_minus),
                                        contentDescription = "Decrease count",

                                        )
                                }

                                Text(
                                    text = configGameState.value.spyCount.toString(),
                                    style = MaterialTheme.typography.headlineMedium,

                                    )

                                Box(
                                    modifier = Modifier
                                        .size(AppDimens.spacingXl)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = AppShapes.small
                                        )
                                        .clickable { vm.onAction(ConfigGameContract.ConfigGameAction.IncreaseSpyCount) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(AppDimens.iconMedium),
                                        painter = painterResource(R.drawable.icon_plus),
                                        contentDescription = "Increase count",

                                        )
                                }
                            }
                            Text(text = "9587295729857123")
                        }

                    }
                }
            }
        }
    }
}

