package com.nikhilchauhan.locationtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikhilchauhan.locationtracker.ui.locationscreen.LocationTrackingScreen
import com.nikhilchauhan.locationtracker.ui.loginscreen.LoginScreen

@Composable
fun TrackerNavigation(startDest: String) {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = startDest) {
    composable(NavRoutes.Login.route) {
      LoginScreen(navController = navController)
    }
    composable(NavRoutes.Location.route) {
      LocationTrackingScreen()
    }
  }
}

sealed class NavRoutes(
  val route: String,
  val name: String
) {
  object Login : NavRoutes("login_screen", "Login Screen")
  object Location : NavRoutes("location_screen", "Location Screen")
}