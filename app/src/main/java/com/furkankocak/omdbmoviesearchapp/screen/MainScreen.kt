package com.furkankocak.omdbmoviesearchapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel


// Ana arama ekranı
@Composable
fun SearchMovieScreen(
    vmInput: MainViewModel,
    onDetailsButtonClick: () -> Unit
) {

    //TextField dan çıkış ve klavye gizlemek için clear focus
    val focusManager = LocalFocusManager.current

    //TextField daki text in State ini tutmak için
    var searchValue by rememberSaveable { mutableStateOf("") }

    Scaffold(content = {
        Column(modifier = Modifier
            .padding(16.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) })
        {
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField( //Arama yapılacak TextField
                        value = searchValue,
                        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black),
                        onValueChange = { searchValue = it },
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .fillMaxHeight()
                            .weight(1f),
                        label = { Text("Search Movie Title") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        },
                        shape = RoundedCornerShape(30.dp)

                    )
                    Button( //SEARCH BUTONU
                        onClick = { //TextField içerisindeki değer ile retrofit sorgusu ve clearfocus
                            vmInput.getMovieList(searchValue)
                            focusManager.clearFocus()
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
            Box{
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(vmInput.listOfMovies.movieList) { movie ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                )
                                {

                                    Row() {
                                        Image( //Listedeki posterler
                                            painter = rememberAsyncImagePainter(movie.poster),
                                            contentDescription = null,
                                            Modifier
                                                .width(50.dp)
                                                .height(100.dp)
                                        )
                                        Column() {
                                            Text( //Liste film başlıkları
                                                movie.title,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                color = Color.Black,
                                                modifier = Modifier.padding(10.dp),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Row() {
                                                Text( //Liste film yıl ve tipi
                                                    movie.year!! + " " + "(${movie.type})",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = Color.Black,
                                                    modifier = Modifier.padding(10.dp),
                                                    fontSize = 20.sp,
                                                    fontStyle = FontStyle.Italic
                                                )
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.BottomEnd
                                                ) {
                                                    Button(onClick = { //POPUP ekranı için true değeri ve detay requesti
                                                        onDetailsButtonClick()
                                                        focusManager.clearFocus()
                                                        vmInput.getMovieSpecs(movie.imdbID.toString())
                                                    })
                                                    { Text(text = "Details") }
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                            Divider()
                        }

                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White,
                                    Color.White,
                                    Color.Transparent
                                )
                            ), alpha = 1f
                        )
                ) {}
            }
        }

    }
    )
}