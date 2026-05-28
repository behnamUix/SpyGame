package com.behnamuix.spy.ui.navigation.screens.confiGame.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.behnamuix.retrofittest.SpyGame.viewModel.ConfigGameViewModel
import com.behnamuix.spy.data.local.db.model.KeyWord

@Composable
fun ListKeyWordsComp(
    vm: ConfigGameViewModel,
    listWord: MutableList<KeyWord>,
) {
    var count by remember { mutableIntStateOf(-1) }
    val ctx = LocalContext.current

    Column(Modifier.padding(8.dp)) {
        //list word
        if (listWord.isEmpty()) {

            Text(
                style = MaterialTheme.typography.labelLarge,
                text = "پایگاه داده کلماتت خالیه!",
                color = Color.Black.copy(0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
            ) {
                itemsIndexed(listWord) { index, item ->
                    OutlinedCard(
                        border = BorderStroke(
                            1.dp, color = if (count == index) {
                                Color.White.copy(0.3f)
                            } else {
                                Color.Transparent
                            }
                        ),
                        onClick = { count = index },
                        elevation = CardDefaults.elevatedCardElevation(6.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            IconButton({
                                try {
                                    vm.deleteWord(
                                        id = item.id
                                    )
                                    listWord.removeAt(index)
                                } catch (e: IndexOutOfBoundsException) {
                                    Toast.makeText(
                                        ctx,
                                        "مشکلی موقتی در زمان حذف کلمه پیش آمد!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                vm.getWords()
                                Toast.makeText(
                                    ctx,
                                    " کلمه ${item.word} از کلمات بازی حذف شد ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(
                                    tint = Color(0xFFEF5350),
                                    modifier = Modifier.size(22.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = ""
                                )

                            }

                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp),
                                text = item.word,


                                )
                        }


                    }
                }
            }
            Log.d("LOG", listWord.toString())

        }
    }


}
