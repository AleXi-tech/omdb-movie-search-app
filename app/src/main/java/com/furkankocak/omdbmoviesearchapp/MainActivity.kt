package com.furkankocak.omdbmoviesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.compose.rememberAsyncImagePainter
import com.furkankocak.omdbmoviesearchapp.data.Movie
import com.furkankocak.omdbmoviesearchapp.ui.theme.OMDbMovieSearchAppTheme
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel
import com.furkankocak.omdbmoviesearchapp.viewmodel.MovieListState

private var showMovieWindow = mutableStateOf(false)

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OMDbMovieSearchAppTheme {
                SearchMovieScreen(viewModel)
                if (showMovieWindow.value) {
                    PopUpInfo(viewModel)
                }
            }
        }
    }
}

@Composable
fun SearchMovieScreen(vmInput: MainViewModel) {

    showMovieWindow = rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    var searchValue by rememberSaveable { mutableStateOf("") }

    fun detailsButton(movie: Movie) {
        showMovieWindow.value = true
        focusManager.clearFocus()
        vmInput.movieSpecs.clear()
        vmInput.getMovieSpecs(movie.imdbID.toString())
    }

    fun searchButton() {
        vmInput.getMovieList(searchValue)
        focusManager.clearFocus()
    }

    Scaffold(content = {
        Column(modifier = Modifier
            .padding(16.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) })
        {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row() {
                    OutlinedTextField(
                        value = searchValue,
                        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black),
                        onValueChange = { searchValue = it },
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(70.dp),
                        label = { Text("Search Movie Title") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        },

                        )
                    Button(
                        onClick = { searchButton() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(3.dp)
                    ) {
                        Text(text = "SEARCH")
                    }
                }
            }
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
                                    Image(
                                        painter = rememberAsyncImagePainter(movie.poster),
                                        contentDescription = null,
                                        Modifier
                                            .width(50.dp)
                                            .height(100.dp)
                                    )
                                    Column() {
                                        Text(
                                            movie.title,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = Color.Black,
                                            modifier = Modifier.padding(10.dp),
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row() {
                                            Text(
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
                                                Button(onClick = { detailsButton(movie) })
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
        }

    }
    )
}

@Composable
fun PopUpInfo(viewModel: MainViewModel) {

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        Popup(alignment = Alignment.Center) {
            Row() {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { showMovieWindow.value = false },
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
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
                    .border(BorderStroke(2.dp, Color.Red), shape = RoundedCornerShape(25.dp))
                    .background(Color.White, RoundedCornerShape(25.dp))
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.scrollable(listState, orientation = Orientation.Vertical)
                ) {
                    items(viewModel.movieSpecs) { specs ->
                        Column {
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
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
                                Text(
                                    text = specs.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            }
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Row() {
                                    Text(buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("IMDb Rating :")
                                        }
                                        append(specs.imdbRating!!)
                                    })
                                    Text(text = "", modifier = Modifier.padding(horizontal = 10.dp))
                                    Text(buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Duration :")
                                        }
                                        append(specs.runtime!!)
                                    })
                                }
                            }
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Actors :")
                                }
                                append(specs.actors!!)
                            }, modifier = Modifier.padding(horizontal = 20.dp))
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
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
                                Text(
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
