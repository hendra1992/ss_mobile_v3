package com.softwaresekolah.inosoft.core.presentation.SsNavigator

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.core.Util.OffsetWrapper
import com.softwaresekolah.inosoft.core.data.NavItem
import com.softwaresekolah.inosoft.core.presentation.SsNavigator.components.BottomNavBar
import com.softwaresekolah.inosoft.core.presentation.SsNavigator.components.ListAccount
import com.softwaresekolah.inosoft.core.presentation.SsNavigator.components.NavDraw
import com.softwaresekolah.inosoft.core.presentation.SsNavigator.components.TopBar
import com.softwaresekolah.inosoft.core.presentation.navgraph.Route
import com.softwaresekolah.inosoft.home.presentation.HomeScreen
import com.softwaresekolah.inosoft.notification.data.Notification
import com.softwaresekolah.inosoft.notification.presentation.NotificationScreen
import com.softwaresekolah.inosoft.core.presentation.common.rememberMultiSelectionState
import com.softwaresekolah.inosoft.profile.presentation.ProfileScreen
import com.softwaresekolah.inosoft.settings.presentation.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            NavItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasUpdate = false,
            ),
            NavItem(
                title = "Notification",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                hasUpdate = false,
                badgeCount = 5
            ),
            NavItem(
                title = "Setting",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                hasUpdate = false,
            ),
            NavItem(
                title = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                hasUpdate = false,
            ),
        )
    }

    val accountBottomSheetState = rememberModalBottomSheetState()
    val isAccountBottomSheetOpen = rememberSaveable {
        mutableStateOf(false)
    }
    val accountBottomSheetScaffoldState = rememberBottomSheetScaffoldState()



    val drawerNavigationItems = remember {
        listOf(
            NavItem(
                title = "Rapor Semester",
                selectedIcon = Icons.Filled.Newspaper,
                unselectedIcon = Icons.Outlined.Newspaper,
                hasUpdate = false,
            ),
            NavItem(
                title = "Rapor Bulanan",
                selectedIcon = Icons.Filled.Newspaper,
                unselectedIcon = Icons.Outlined.Newspaper,
                hasUpdate = false,
                badgeCount = 5
            ),
            NavItem(
                title = "Rapor Mingguan",
                selectedIcon = Icons.Filled.Newspaper,
                unselectedIcon = Icons.Outlined.Newspaper,
                hasUpdate = false,
            ),

        )
    }

    val context = LocalContext.current
    val navController = rememberNavController()
    var actionBarTitle by rememberSaveable { mutableStateOf("Home") }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            // You can map the title based on the route using:
            actionBarTitle = getTitleByRoute(context, backStackEntry.destination.route!!)
        }
    }
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.NotificationScreen.route -> 1
        Route.SettingScreen.route -> 2
        else -> 0
    }

    var selectedItemDrawer by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItemDrawer = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.NotificationScreen.route -> 1
        Route.SettingScreen.route -> 2
        Route.ProfileScreen.route -> 3
        else -> 0
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.NotificationScreen.route ||
                backStackState?.destination?.route == Route.SettingScreen.route ||
                backStackState?.destination?.route == Route.ProfileScreen.route
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // notification page state
    val multipleSelectModeState = rememberMultiSelectionState()
    val selectedItems = remember {
        mutableStateListOf<Notification>()
    }
    ModalNavigationDrawer(
        drawerContent = {
            NavDraw(
                items = drawerNavigationItems,
                selectedItem = selectedItemDrawer,
                onItemClick = { page ->
                    when(page) {
                        "Rapor Semester" -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )
                        "Rapor Bulanan" -> navigateToTab(
                            navController = navController,
                            route = Route.NotificationScreen.route
                        )
                        "Rapor Mingguan" -> navigateToTab(
                            navController = navController,
                            route = Route.SettingScreen.route
                        )
                    }
                },
                drawerState = drawerState
            )
        },
        drawerState = drawerState,
    ) {
        Scaffold (
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
            ,
            bottomBar = {
                if (isBottomBarVisible) {
                    BottomNavBar(
                        items = bottomNavigationItems,
                        selectedItem = selectedItem,
                        onItemClick = { index ->
                            when (index) {
                                0 -> navigateToTab(
                                    navController = navController,
                                    route = Route.HomeScreen.route
                                )
                                1 -> navigateToTab(
                                    navController = navController,
                                    route = Route.NotificationScreen.route
                                )

                                2 -> navigateToTab(
                                    navController = navController,
                                    route = Route.SettingScreen.route
                                )
                                3 -> navigateToTab(
                                    navController = navController,
                                    route = Route.ProfileScreen.route
                                )
                            }
                        }
                    )
                }
            },
            topBar = {
                TopBar(
                    title = actionBarTitle,
                    scrollBehavior = scrollBehavior,
                    drawerState = drawerState,
                    multiSelectState = multipleSelectModeState,
                    notifSelectedItem = selectedItems
                )
            }
        ){
            val bottomPadding = it.calculateBottomPadding()
            val topPadding = it.calculateTopPadding()

            if (isAccountBottomSheetOpen.value){
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(),
                    sheetState = accountBottomSheetState,
                    containerColor = Color.White,
                    onDismissRequest = { isAccountBottomSheetOpen.value = false }
                ) {
                    BottomSheetScaffold(
                        scaffoldState = accountBottomSheetScaffoldState,
                        sheetContent = {  },
                        topBar = {
                            Box (modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ){
                                Button(
                                    modifier = Modifier
                                        .padding(24.dp),
                                    onClick = {},
                                    contentPadding = PaddingValues(horizontal = 30.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Tambah Akun",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        },

                    ) {
                        ListAccount()
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = Route.HomeScreen.route,
                modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
            ) {
                composable(route = Route.HomeScreen.route) { backStackEntry ->
                    HomeScreen()
                }
                composable(route = Route.NotificationScreen.route) {
                    NotificationScreen(state = multipleSelectModeState, selectedItems = selectedItems)
                }
                composable(route = Route.SettingScreen.route) {
                    SettingsScreen(sheetState = accountBottomSheetState, isAccountBottomSheetOpen = isAccountBottomSheetOpen)
                }
                composable(route = Route.ProfileScreen.route) {
                    ProfileScreen()
                }
            }
        }
    }
}


@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

private fun getTitleByRoute(context: Context, route:String): String {
    return when (route) {
        Route.HomeScreen.route -> context.getString(R.string.app_name)
        Route.NotificationScreen.route -> "Notification"
        Route.SettingScreen.route -> "Setting"
        Route.ProfileScreen.route -> "Profile"
        // other cases
        else -> "Home"
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}