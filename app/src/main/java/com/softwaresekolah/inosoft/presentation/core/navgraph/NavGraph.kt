package com.softwaresekolah.inosoft.presentation.core.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.softwaresekolah.inosoft.presentation.auth.Login.LoginScreen
import com.softwaresekolah.inosoft.presentation.auth.Login.LoginViewModel
import com.softwaresekolah.inosoft.presentation.boarding.BoardingScreen
import com.softwaresekolah.inosoft.presentation.boarding.BoardingViewModel
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModel
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SsNavigator

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
                val state = viewModel.state.value
                LoginScreen(viewModel::onEvent, state)
            }
        }
    }
}