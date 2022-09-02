package com.furkankocak.omdbmoviesearchapp.screen.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

@Composable
fun SearchResults(
    viewModel: MainViewModel,
    onDetailsButtonClick: () -> Unit
){
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(viewModel.listOfMovies.movieList) { movie ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Box(
                        modifier = Modifier.fillMaxWidth()
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
                            Column(Modifier.fillMaxWidth()) {
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
                                            viewModel.getMovieSpecs(movie.imdbID.toString())
                                        })
                                        { Text(text = "Details") }
                                    }
                                }
                            }
                        }
                    }
                }
                Divider()
            }
        }
    }
}