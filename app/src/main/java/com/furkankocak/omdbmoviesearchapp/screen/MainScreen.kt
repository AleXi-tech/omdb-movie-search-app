package com.furkankocak.omdbmoviesearchapp.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.furkankocak.omdbmoviesearchapp.screen.widget.Fader
import com.furkankocak.omdbmoviesearchapp.screen.widget.SearchBar
import com.furkankocak.omdbmoviesearchapp.screen.widget.SearchResults
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

@Composable
fun SearchMovieScreen(
    vmInput: MainViewModel,
    onDetailsButtonClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Surface {
        Column(modifier = Modifier
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
        )
        {
            SearchBar(
                vmInput = vmInput,
                onSearchClick = {
                    focusManager.clearFocus()
                }
            )
            Box {
                SearchResults(
                    vmInput = vmInput,
                    onDetailsButtonClick = {
                        focusManager.clearFocus()
                        onDetailsButtonClick()
                    }
                )
                Fader()
            }
        }

    }

}