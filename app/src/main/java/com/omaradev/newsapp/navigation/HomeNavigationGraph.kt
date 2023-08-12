package com.omaradev.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omaradev.newsapp.domain.model.news.Article
import com.omaradev.newsapp.ui.details.DetailsScreen
import com.omaradev.newsapp.ui.home.HomeScreen


@Composable
fun HomeNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeNavigation.Home.screen) {
        composable(HomeNavigation.Home.screen) {
            HomeScreen(navController = navController)
        }
        composable(HomeNavigation.Details.screen) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            DetailsScreen(article  = result)
        }
    }
}