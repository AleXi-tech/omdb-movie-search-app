package com.furkankocak.omdbmoviesearchapp

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkankocak.omdbmoviesearchapp.navigator.Navigation
import com.furkankocak.omdbmoviesearchapp.screen.PopUpInfo
import com.furkankocak.omdbmoviesearchapp.screen.SearchMovieScreen
import com.furkankocak.omdbmoviesearchapp.screen.SplashScreen
import com.furkankocak.omdbmoviesearchapp.ui.theme.OMDbMovieSearchAppTheme
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            OMDbMovieSearchAppTheme {
                Navigation(viewModel)
                /**
                 * POPUP penceresinin açılması için showMovieWindow adında State oluşturduk
                 * ve DETAILS ve EXIT butonlarının onClick metodları içerisnde boolean
                 * değerini kontrol ederek if kontrolü ile açılıp kapanmasını denetliyoruz.
                 */
                if (viewModel.showMovieWindow) {
                    viewModel.movieSpecs.movieDetail?.let {
                        PopUpInfo(
                            it,
                            onExitButtonClick = { viewModel.onShowMovieWindowChange(false) }
                        )
                    }
                }
            }
        }
    }
}



