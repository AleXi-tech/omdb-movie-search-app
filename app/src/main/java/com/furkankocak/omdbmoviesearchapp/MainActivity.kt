package com.furkankocak.omdbmoviesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.furkankocak.omdbmoviesearchapp.data.MovieDetail
import com.furkankocak.omdbmoviesearchapp.ui.theme.OMDbMovieSearchAppTheme
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OMDbMovieSearchAppTheme {
                Screen(vmInput = viewModel)
            }
        }
    }
}

@Composable
fun Screen(vmInput: MainViewModel) {

    //POPUP window controller state
    var showMovieWindow by rememberSaveable { mutableStateOf(false) }

    //TextField state holder
    var searchValue by rememberSaveable { mutableStateOf("") }

    SearchMovieScreen(
        vmInput = vmInput,
        searchValueStr = searchValue,
        onValueChangeFun = { searchValue = it },
        showMovieWindowFun = { showMovieWindow = it }
    )

    /**
     * POPUP penceresinin a????lmas?? i??in showMovieWindow ad??nda State olu??turduk
     * ve DETAILS ve EXIT butonlar??n??n onClick metodlar?? i??erisnde boolean
     * de??erini kontrol ederek if kontrol?? ile a????l??p kapanmas??n?? denetliyoruz.
     */
    if (showMovieWindow) {
        vmInput.movieSpecs.let {
            if (it != null) {
                PopUpInfo(it, showMovieWindowFun = { showMovieWindow = it })
            }
        }
    }

}


// Main movie search screen
@Composable
fun SearchMovieScreen(
    vmInput: MainViewModel,
    searchValueStr: String,
    onValueChangeFun: (String) -> Unit,
    showMovieWindowFun: (Boolean) -> Unit
) {

    //TextField exit and clear focus variable
    val focusManager = LocalFocusManager.current

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
                    //Arama yap??lacak TextField
                    value = searchValueStr,
                    colors = TextFieldDefaults.textFieldColors(textColor = Color.Black),
                    onValueChange = {
                        onValueChangeFun(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(70.dp),
                    label = { Text("Search Movie Title") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    },

                    )
                Button( //SEARCH BUTTON
                    onClick = { //TextField i??erisindeki de??er ile retrofit sorgusu ve clearfocus
                        vmInput.getMovieList(searchValueStr)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(3.dp)
                ) {
                    Text(text = "SEARCH")
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(vmInput.listOfMovies) { movie ->
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
                                    Text( //Liste film ba??l??klar??
                                        movie.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.Black,
                                        modifier = Modifier.padding(10.dp),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row() {
                                        Text( //Liste film y??l ve tipi
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
                                            Button(onClick = { //POPUP ekran?? i??in true de??eri ve detay requesti
                                                showMovieWindowFun(true)
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
    }

}


/**
 * Ekrana gelen sonu??lar i??erisinde DETAILS butonuna t??klama sonucunda a????lacak
 * olan POPUP film detay?? penceremiz
 * */
@Composable
fun PopUpInfo(
    specs: MovieDetail,
    showMovieWindowFun: (Boolean) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Popup(alignment = Alignment.Center) {
            Row() {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(  //POPUP ??IKI?? BUTONU
                        onClick = { showMovieWindowFun(false) }, //POPUP pencere kapatma
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
                    .border(
                        BorderStroke(2.dp, Color.Red),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color.White, RoundedCornerShape(25.dp))
            ) {
                LazyColumn(
                ) {
                    item {  // specs de??eri do??rudan constructor i??erisinden ??a????r??l??yor
                        Column {
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image( //POPUP F??LM POSTER??
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
                                Text( //POPUP F??LM BA??LI??I
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
                                    Text(buildAnnotatedString { //POPUP F??LM IMDb PUANI
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
                                    Text(buildAnnotatedString { //POPUP F??LM S??RES??
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
                            Text(buildAnnotatedString { //POPUP F??LM AKT??RLER??
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Actors :")
                                }
                                append(specs.actors!!)
                            }, modifier = Modifier.padding(horizontal = 20.dp))
                            Text(buildAnnotatedString { //POPUP F??LM Y??NETMEN??
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
                                Text( //POPUP F??LM H??KAYE ????ER??????
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
