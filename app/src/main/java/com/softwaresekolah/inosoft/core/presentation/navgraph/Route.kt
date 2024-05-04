package com.softwaresekolah.inosoft.core.presentation.navgraph

sealed class Route(
    val route: String
){
    // boarding screen graph
    object AppStartNavigation : Route(route = "appStartNavigation")
    object BoardingScreen : Route(route = "BoardingScreen")

    // main graph
    object AppSsNavigation : Route(route = "appSsNavigation")
    object SsNavigatorScreen : Route(route = "SsNavigator")
    object HomeScreen : Route(route = "HomeScreen")
    object NotificationScreen : Route(route = "NotificationScreen")
    object SettingScreen : Route(route = "SettingScreen")
    object ProfileScreen : Route(route = "ProfileScreen")

    //auth graph
    object AppAuthNavigation : Route(route = "appAuthNavigation")
    object LoginScreen : Route(route = "LoginScreen")

}