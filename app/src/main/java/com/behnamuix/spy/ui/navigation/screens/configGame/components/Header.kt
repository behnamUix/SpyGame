package com.behnamuix.spy.ui.navigation.screens.configGame.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.behnamuix.spy.authentication.viewModel.AuthViewModel
import com.behnamuix.spy.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spy.utils.setLog
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Header(
    authVm: AuthViewModel = koinViewModel(),
    dsVm: DataStoreViewModel = koinViewModel(),
    vm: ConfigGameViewModel = koinViewModel(),
) {
    val ctx = LocalContext.current
    val userProfile = authVm.currentUserProfile.collectAsState()
    val mediaState by vm.mediaState.collectAsState()
    val expandedState = vm.expanded.collectAsState()
    var check by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow
        ), label = "rotation" // اضافه کردن label برای بهتر شدن لاگ‌ها
    )

    LaunchedEffect(Unit) {
        vm.userUseOperation(dsVm, setCheck = { check = it })

    }
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(8.dp), onClick = {
                vm.reverseExpand()
            }) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Default.Settings,
                modifier = Modifier
                    .size(32.dp)

                    .rotate(rotationAngle),
                contentDescription = ""
            )
        }
        if (userProfile.value == null) {
            Text(
                text = "ورود انجام نشده",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        authVm.signInWithGoogle(
                            ctx,
                            onSuccess = {
                                Toast.makeText(
                                    ctx,
                                    "ورود با موفقیت انجام شد",
                                    Toast.LENGTH_SHORT
                                ).show()
                                authVm.updateCurrentUser()

                            },
                            onFailed = {
                                Toast.makeText(ctx, "خطا در ورود: $it", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )


                    },
                textAlign = TextAlign.Center
            )
        } else {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = userProfile.value?.photoUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = userProfile.value?.name ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFFFFF00),
                )

            }

        }

        MediaControllerComp(mediaState, vm)
    }
    AnimatedVisibility(
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
    }

}