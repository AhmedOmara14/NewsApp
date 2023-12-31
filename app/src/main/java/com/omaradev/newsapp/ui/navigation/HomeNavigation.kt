package com.omaradev.newsapp.ui.navigation

sealed class HomeNavigation(var screen: String) {

    object Home : HomeNavigation("Home")

    object Details : HomeNavigation("Details")

}
