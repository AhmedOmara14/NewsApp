package com.omaradev.newsapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omaradev.newsapp.ui.home.HomeScreen
import com.omaradev.newsapp.ui.details.DetailsScreen


@Composable
fun HomeNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeNavigation.Home.screen) {
        composable(HomeNavigation.Home.screen) {
            HomeScreen(navController=navController)
        }
        composable(HomeNavigation.Details.screen) {
            DetailsScreen()
        }
    }
}