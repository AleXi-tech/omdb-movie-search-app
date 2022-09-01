package com.furkankocak.omdbmoviesearchapp

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkankocak.omdbmoviesearchapp.screen.PopUpInfo
import com.furkankocak.omdbmoviesearchapp.screen.SearchMovieScreen
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

@Composable
fun Navigation(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            Box(Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Color.Red)){
                SplashScreen(navController = navController)
            }

        }
        // Main Screen
        composable("main_screen") {
            SearchMovieScreen(
                vmInput = viewModel,
                onDetailsButtonClick = { viewModel.onShowMovieWindowChange(true) }
            )
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
        navController.navigate("main_screen")
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.pngwing),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}