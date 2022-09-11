package com.furkankocak.omdbmoviesearchapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.furkankocak.omdbmoviesearchapp.data.MovieDetail
import com.furkankocak.omdbmoviesearchapp.screen.widget.DetailTopBar

/**
 * Ekrana gelen sonuçlar içerisinde DETAILS butonuna tıklama sonucunda açılacak
 * olan POPUP film detayı penceremiz
 * */
@Composable
fun DetailScreen(
    specs: MovieDetail,
    onExitButtonClick: () -> Unit
) {

    val listState = rememberLazyListState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentColor = Color.White,
        color = Color.Black
    ) {

        Box(contentAlignment = Alignment.TopCenter) {

            LazyColumn(
                state = listState,
                modifier = Modifier.scrollable(
                    listState,
                    orientation = Orientation.Vertical
                )
            ) {
                item {  // specs değeri doğrudan constructor içerisinden çağırılıyor

                    val model = ImageRequest.Builder(LocalContext.current)
                        .data(specs.poster)
                        .size(Size.ORIGINAL)
                        .crossfade(true)
                        .build()
                    val painter = rememberAsyncImagePainter(model)

                    Column {

                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize().padding(24.dp)
                        ) {

                            Image( //POPUP FİLM POSTERİ
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.FillWidth
                            )

                            Box( //Bottom fade effect over poster
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black)
                                        )
                                    )
                            )
                        }

                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text( //POPUP FİLM BAŞLIĞI
                                text = specs.title!!,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Row {
                                Text(buildAnnotatedString { //POPUP FİLM IMDb PUANI
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("IMDb Rating :")
                                    }
                                    append(specs.imdbRating!!)
                                })
                                Text(
                                    text = "", modifier = Modifier.padding(
                                        horizontal = 10.dp
                                    )
                                )
                                Text(buildAnnotatedString { //POPUP FİLM SÜRESİ
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("Duration :")
                                    }
                                    append(specs.runtime!!)
                                })
                            }
                        }
                        Text(buildAnnotatedString { //POPUP FİLM AKTÖRLERİ
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Actors :")
                            }
                            append(specs.actors!!)
                        }, modifier = Modifier.padding(horizontal = 20.dp))
                        Text(buildAnnotatedString { //POPUP FİLM YÖNETMENİ
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Director :")
                            }
                            append(specs.director!!)
                        }, modifier = Modifier.padding(horizontal = 20.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "STORY", fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text( //POPUP FİLM HİKAYE İÇERİĞİ
                                text = specs.plot!!,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .padding(bottom = 30.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            DetailTopBar(
                onExitButtonClick = onExitButtonClick
            )
        }
    }
}

