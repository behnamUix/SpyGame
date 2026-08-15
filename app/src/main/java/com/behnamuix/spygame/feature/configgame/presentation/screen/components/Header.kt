package com.behnamuix.spygame.feature.configgame.presentation.screen.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.utils.setLog
import com.behnamuix.spygame.feature.configgame.presentation.viewmodel.ConfigGameViewModel
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.koinViewModel

@Composable
fun Header(
    navController: NavController,
    dsVm: DataStoreViewModel = koinViewModel(),
    vm: ConfigGameViewModel = koinViewModel(),
) {
    var login by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
   // val mediaState by vm.mediaState.collectAsState()
   //val expandedState = vm.expanded.collectAsState()
    var check by remember { mutableStateOf(false) }
    /*val rotationAngle by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        ), label = "rotation" // اضافه کردن label برای بهتر شدن لاگ‌ها
    )*/
    var loggedIn by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        login = if (dsVm.loggedInState.first()) true else false
        vm.userUseOperation(dsVm, setCheck = { check = it })
        loggedIn = dsVm.loggedInState.first()

    }
    Row(
        modifier = Modifier
            .padding(16.dp,end=16.dp, top = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.padding(8.dp), onClick = {
                vm.reverseExpand()
            }) {
            /*Icon(
                tint = Color.White,
                imageVector = Icons.Default.Settings,
                modifier = Modifier
                    .size(32.dp)

                    .rotate(rotationAngle),
                contentDescription = ""
            )*/
        }

        // Login Status Section
       /* SuggestionChip(
            onClick = { navController.navigate(Screens.OtpLogin.route) },
            label = {
                Text(
                    text = if (login) "پروفایل کاربری" else "ورود / ثبت‌نام",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (login) Color.White else Color.White.copy(alpha = 0.7f)
                )
            },
            icon = {
                Icon(
                    imageVector = if (login) Icons.Default.CheckCircle else Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = if (login) Color.Green else Color.White
                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(40.dp)
        )*/

       // MediaControllerComp(mediaState, vm)
    }
/*    AnimatedVisibility(
        expandedState.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        OutlinedCard(Modifier.padding(16.dp)) {
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.End,
                    text = "میخوای بازی از کلمات تو استفاده کنه",
                    modifier = Modifier.weight(0.8f)
                )
                Checkbox(
                    checked = check,
                    onCheckedChange = { isChecked ->
                        dsVm.setUserUse(isChecked)
                        check = isChecked
                        setLog(value = check)

                        if (check && vm.checkDb()) {
                            vm.setEnabled(false)
                            Toast.makeText(ctx, "لیست کلماتت خالیه", Toast.LENGTH_SHORT).show()
                        } else {
                            vm.setEnabled(true)
                        }
                    }
                )
            }
        }
    }*/

}