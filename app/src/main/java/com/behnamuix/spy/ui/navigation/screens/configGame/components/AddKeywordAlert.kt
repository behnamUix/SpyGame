package com.behnamuix.spy.ui.navigation.screens.configGame.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.behnamuix.spy.data.local.db.model.KeyWord
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import kotlinx.coroutines.launch

@Composable
fun AddKeyWordAlert(
    showAddWordDialog: MutableState<Boolean>,
    configGameViewModel: ConfigGameViewModel,
    ctx: Context,
    listWord: List<KeyWord>,
) {
    val scope = rememberCoroutineScope()
    var word by remember { mutableStateOf("") }
    var wordExist by remember { mutableStateOf(configGameViewModel.wordExist.value) }
    Dialog(
        { showAddWordDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(colors = CardDefaults.cardColors(Color.Black)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                    text = "هر کلمه ای بخوای میتونی به بازی اضافه کنی و از بازی بیشتر لذت ببری",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    isError = wordExist,
                    supportingText = { if (wordExist) Text(" ببخشید کلمه $word در بازی وجود داره:( ") },
                    textStyle = TextStyle(
                        textDirection = TextDirection.Rtl, textAlign = TextAlign.Right
                    ),
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "کلمه را وارد کنید",
                            textAlign = TextAlign.Right
                        )
                    },
                    value = word,
                    onValueChange = { word = it })
                Row(Modifier.padding(horizontal = 16.dp)) {
                    Button(
                        onClick = {

                            showAddWordDialog.value = false
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFEF5350)),
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "بیخیال",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.width(16.dp))
                    Button(
                        modifier = Modifier.weight(0.6f),
                        onClick = {
                            scope.launch {
                                if (word.isNotEmpty()) {
                                    if (!wordExist) {
                                        configGameViewModel.addWord(
                                            KeyWord(word = word)
                                        )
                                        word = ""

                                        Toast.makeText(
                                            ctx, " کلمه $word به بازی اضافه شد ", Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                }
                            }


                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF66BB6A)),
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "اضافه کن",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Black.copy(0.2f),
                    modifier = Modifier.padding(8.dp)
                )
                ListKeyWordsComp(configGameViewModel, listWord)
            }
        }
    }
}