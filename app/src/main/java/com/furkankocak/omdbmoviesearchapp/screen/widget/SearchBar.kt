package com.furkankocak.omdbmoviesearchapp.screen.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

@Composable
fun SearchBar(
    viewModel: MainViewModel,
    onSearchClick: () -> Unit
) {

    var searchValue by rememberSaveable { mutableStateOf("") }

    val outlineWhite = Color.White.copy(alpha = 0.5f)

    Box(
        modifier = Modifier.fillMaxWidth().height(68.dp).padding(bottom = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField( //Arama yapılacak TextField
                value = searchValue,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White.copy(alpha = 0.75f),
                    leadingIconColor = outlineWhite,
                    unfocusedLabelColor = outlineWhite,
                    unfocusedIndicatorColor = outlineWhite,
                ),
                onValueChange = { searchValue = it },
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight()
                    .weight(1f),
                label = { Text("Search Movie Title") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                shape = RoundedCornerShape(30.dp)

            )
            Button( //SEARCH BUTONU
                onClick = { //TextField içerisindeki değer ile retrofit sorgusu ve clearfocus
                    viewModel.getMovieList(searchValue)
                    onSearchClick()
                },
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 4.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "SEARCH")
            }
        }
    }
}