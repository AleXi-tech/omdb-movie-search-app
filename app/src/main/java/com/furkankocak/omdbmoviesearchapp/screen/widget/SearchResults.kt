package com.furkankocak.omdbmoviesearchapp.screen.widget

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResults(
    viewModel: MainViewModel,
    onDetailsButtonClick: () -> Unit
) {

    val listWhite = Color.White.copy(alpha = 0.85f)

    var currentIndex by remember { mutableStateOf(-1) }


    LazyVerticalGrid(
        modifier = Modifier.fillMaxHeight(),
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(viewModel.listOfMovies.movieList) { index, movie ->

            var detailActive by remember { mutableStateOf(false) }
            val detailBottomHeight: Dp by animateDpAsState(if (currentIndex == index) 80.dp else 0.dp)
            val detailTopHeight: Dp by animateDpAsState(if (currentIndex == index) 40.dp else 0.dp)

            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Image( //Listedeki posterler
                    painter = rememberAsyncImagePainter(movie.poster),
                    contentDescription = null,
                    Modifier
                        .width(50.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier
                    .width(50.dp)
                    .height(200.dp)
                    .clickable {
                        if (currentIndex == index)
                            currentIndex = -1
                        else
                            currentIndex = index

                        viewModel.getMovieSpecs(movie.imdbID.toString())
                    }
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(detailTopHeight)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black,
                                            Color.Black,
                                            Color.Black,
                                            Color.Transparent
                                        ),
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            viewModel.movieSpecs.movieDetail?.imdbRating?.let {
                                Text(
                                    text = "IMDb $it",
                                    style = TextStyle(
                                        color = Color(255, 184, 28),
                                        fontFamily = FontFamily.SansSerif,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .border(
                                            border = BorderStroke(1.dp, Color(255, 184, 28)),
                                            shape = RoundedCornerShape(5.dp)
                                        ).height(20.dp)
                                        .width(80.dp)
                                )
                            }


                        }
                        Spacer(Modifier.weight(1f))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(detailBottomHeight)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black,
                                            Color.Black,
                                            Color.Black,
                                            Color.Black,
                                        ),
                                    )
                                ),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Text( //Liste film başlıkları
                                    movie.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = listWhite,
                                    modifier = Modifier.padding(top = 10.dp),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                        .height(20.dp)
                                        .width(60.dp)
                                        .clickable {
                                            onDetailsButtonClick()
                                            viewModel.getMovieSpecs(movie.imdbID.toString())
                                        }
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(MaterialTheme.colors.primary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Details",
                                        style = TextStyle(
                                            color = Color.White.copy(alpha = 0.9f),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
//            Column {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                )
//                {
//                    Box(
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    {
//                        Row() {
//                            Image( //Listedeki posterler
//                                painter = rememberAsyncImagePainter(movie.poster),
//                                contentDescription = null,
//                                Modifier
//                                    .width(50.dp)
//                                    .height(100.dp)
//                            )
//                            Column(Modifier.fillMaxWidth()) {
//                                Text( //Liste film başlıkları
//                                    movie.title,
//                                    maxLines = 1,
//                                    overflow = TextOverflow.Ellipsis,
//                                    color = listWhite,
//                                    modifier = Modifier.padding(10.dp),
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                                Spacer(Modifier.weight(1f))
//                                Row(verticalAlignment = Alignment.Bottom) {
//                                    Text( //Liste film yıl ve tipi
//                                        movie.year!! + " " + "(${movie.type})",
//                                        maxLines = 1,
//                                        overflow = TextOverflow.Ellipsis,
//                                        color = listWhite,
//                                        modifier = Modifier.padding(start = 10.dp, bottom = 4.dp),
//                                        fontSize = 12.sp,
//                                        fontStyle = FontStyle.Italic
//                                    )
//                                    Spacer(Modifier.weight(1f))
//                                    Button(
//                                        onClick = { //POPUP ekranı için true değeri ve detay requesti
//                                            onDetailsButtonClick()
//                                            viewModel.getMovieSpecs(movie.imdbID.toString())
//                                        },
//                                        modifier = Modifier.wrapContentWidth()
//                                    )
//                                    { Text(text = "Details") }
//
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}