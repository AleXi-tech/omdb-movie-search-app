package com.furkankocak.omdbmoviesearchapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.furkankocak.omdbmoviesearchapp.screen.widget.SearchBar
import com.furkankocak.omdbmoviesearchapp.screen.widget.SearchResults
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

@Composable
fun SearchMovieScreen(
    viewModel: MainViewModel,
    onDetailsButtonClick: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Surface(color = Color.Black, contentColor = Color.White) {
        Column(modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
        )
        {
            SearchBar(
                viewModel = viewModel,
                onSearchClick = {
                    focusManager.clearFocus()
                }
            )
            Box {
                SearchResults(
                    viewModel = viewModel,
                    onDetailsButtonClick = {
                        focusManager.clearFocus()
                        onDetailsButtonClick()
                    }
                )
            }
        }

    }

}