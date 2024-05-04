package com.softwaresekolah.inosoft.core.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.softwaresekolah.inosoft.auth.presentation.Login.LoginScreen
import com.softwaresekolah.inosoft.auth.presentation.Login.LoginViewModel
import com.softwaresekolah.inosoft.boarding.presentation.boarding.BoardingScreen
import com.softwaresekolah.inosoft.boarding.presentation.boarding.BoardingViewModel
import com.softwaresekolah.inosoft.core.presentation.SsNavigator.SsNavigator

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.BoardingScreen.route
        ) {
            composable(route = Route.BoardingScreen.route) {
                val viewModel: BoardingViewModel = hiltViewModel()
                BoardingScreen(onEvent = viewModel::onEvent)
            }
        }

        navigation(
            route = Route.AppSsNavigation.route,
            startDestination = Route.SsNavigatorScreen.route
        ) {
            composable(route = Route.SsNavigatorScreen.route){
                SsNavigator()
            }
        }

        navigation(
            route = Route.AppAuthNavigation.route,
            startDestination = Route.LoginScreen.route
        ) {
            composable(route = Route.LoginScreen.route){
                val viewModel: LoginViewModel = hiltViewModel()
                LoginScreen(viewModel::onEvent)
            }
        }
    }
}