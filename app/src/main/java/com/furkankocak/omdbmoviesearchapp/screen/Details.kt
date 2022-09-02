package com.furkankocak.omdbmoviesearchapp.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.compose.rememberAsyncImagePainter
import com.furkankocak.omdbmoviesearchapp.data.MovieDetail

/**
 * Ekrana gelen sonuçlar içerisinde DETAILS butonuna tıklama sonucunda açılacak
 * olan POPUP film detayı penceremiz
 * */
@Composable
fun PopUpInfo(
    specs: MovieDetail,
    onExitButtonClick: () -> Unit
) {

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        Popup(alignment = Alignment.Center) {
            Row() {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(  //POPUP ÇIKIŞ BUTONU
                        onClick = onExitButtonClick, //POPUP pencere kapatma
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color.White, RoundedCornerShape(25.dp))
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.scrollable(listState,
                        orientation = Orientation.Vertical)
                ) {
                    item {  // specs değeri doğrudan constructor içerisinden çağırılıyor
                        Column {
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image( //POPUP FİLM POSTERİ
                                    painter = rememberAsyncImagePainter(specs.poster),
                                    contentDescription = null,
                                    Modifier
                                        .wrapContentWidth()
                                        .height(260.dp)
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
                                Row() {
                                    Text(buildAnnotatedString { //POPUP FİLM IMDb PUANI
                                        withStyle(style = SpanStyle(
                                            fontWeight = FontWeight.Bold)
                                        ) {
                                            append("IMDb Rating :")
                                        }
                                        append(specs.imdbRating!!)
                                    })
                                    Text(
                                        text = "", modifier = Modifier.padding(horizontal = 10.dp
                                        ))
                                    Text(buildAnnotatedString { //POPUP FİLM SÜRESİ
                                        withStyle(style = SpanStyle(
                                            fontWeight = FontWeight.Bold)
                                        ) {
                                            append("Duration :")
                                        }
                                        append(specs.runtime!!)
                                    })
                                }
                            }
                            Text(buildAnnotatedString { //POPUP FİLM AKTÖRLERİ
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold)
                                ) {
                                    append("Actors :")
                                }
                                append(specs.actors!!)
                            }, modifier = Modifier.padding(horizontal = 20.dp))
                            Text(buildAnnotatedString { //POPUP FİLM YÖNETMENİ
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold)
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
            }
        }
    }
}