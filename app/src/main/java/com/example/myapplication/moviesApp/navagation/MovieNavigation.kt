package com.example.myapplication.moviesApp.navagation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.moviesApp.screens.detail.DetailsScreen
import com.example.myapplication.moviesApp.screens.home.HomeScreen

@Composable
    fun MovieNavigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        NavHost(navController  = navController,
            startDestination = MovieScreens.HomeScreen.name){
            composable (MovieScreens.HomeScreen.name) {
                HomeScreen(navController = navController)
            }

            composable (MovieScreens.DetailsScreen.name+"/{movie}",
                arguments = listOf(navArgument(name = "movie"){
                    type = NavType.StringType
                })){
                backStackEntry ->
                DetailsScreen(navController , backStackEntry.arguments?.getString("movie"))
            }
        }
    }
