package com.furkankocak.omdbmoviesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.furkankocak.omdbmoviesearchapp.navigator.Navigation
import com.furkankocak.omdbmoviesearchapp.screen.DetailScreen
import com.furkankocak.omdbmoviesearchapp.ui.theme.OMDbMovieSearchAppTheme
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            OMDbMovieSearchAppTheme {
                Navigation(viewModel)

                //Detail screen controller
                if (viewModel.showMovieWindow) {
                    viewModel.movieSpecs.movieDetail?.let {
                        DetailScreen(
                            it,
                            onExitButtonClick = { viewModel.onShowMovieWindowChange(false) }
                        )
                    }
                }
            }
        }

    }
}



