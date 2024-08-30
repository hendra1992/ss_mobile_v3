package com.softwaresekolah.inosoft.presentation.core.navgraph

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
    object ListAccountScreen : Route(route = "ListAccountScreen")
    object ProfileScreen : Route(route = "ProfileScreen")
    object DataDiriScreen : Route(route = "DataDiriScreen")
    object DataOrangTuaScreen : Route(route = "DataOrangTuaScreen")
    object FileKelengkapanSiswaScreen : Route(route = "FileKelengkapanSiswaScreen")
    object AlamatScreen : Route(route = "AlamatScreen")
    object LainnyaScreen : Route(route = "LainnyaScreen")
    object DrawDetailScreen : Route(route = "DrawDetailScreen")
    object NotificationDetailScreen : Route(route = "NotificationDetailScreen")


    //auth graph
    object AppAuthNavigation : Route(route = "appAuthNavigation")
    object LoginScreen : Route(route = "LoginScreen")
    object LoginExpScreen : Route(route = "LoginExpScreen")



}