package com.furkankocak.omdbmoviesearchapp.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkankocak.omdbmoviesearchapp.screen.SearchMovieScreen
import com.furkankocak.omdbmoviesearchapp.screen.SplashScreen
import com.furkankocak.omdbmoviesearchapp.viewmodel.MainViewModel

@Composable
fun Navigation(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        // Splash Screen
        composable("splash_screen") {
            Box(Modifier.fillMaxSize().background(Color.Black)) {
                SplashScreen(navController = navController)
            }

        }
        // Main Screen
        composable("main_screen") {
            SearchMovieScreen(
                viewModel = viewModel,
                onDetailsButtonClick = { viewModel.onShowMovieWindowChange(true) }
            )
        }
    }
}