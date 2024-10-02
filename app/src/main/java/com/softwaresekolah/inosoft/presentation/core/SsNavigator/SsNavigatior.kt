package com.softwaresekolah.inosoft.presentation.core.SsNavigator

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.data.core.BottomNavItem
import com.softwaresekolah.inosoft.data.core.NavItem
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.components.BottomNavBar
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.components.NavDraw
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.components.TopBar
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.presentation.home.HomeScreen
import com.softwaresekolah.inosoft.data.notification.Notification
import com.softwaresekolah.inosoft.presentation.auth.Login.LoginScreen
import com.softwaresekolah.inosoft.presentation.auth.Login.LoginViewModel
import com.softwaresekolah.inosoft.presentation.auth.expLogin.LoginExpScreen
import com.softwaresekolah.inosoft.presentation.auth.expLogin.LoginExpViewModel
import com.softwaresekolah.inosoft.presentation.notification.list.NotificationScreen
import com.softwaresekolah.inosoft.presentation.core.common.rememberMultiSelectionState
import com.softwaresekolah.inosoft.presentation.drawer.DrawDetailScreen
import com.softwaresekolah.inosoft.presentation.notification.detail.NotificationDetailScreen
import com.softwaresekolah.inosoft.presentation.profile.address.AddressScreen
import com.softwaresekolah.inosoft.presentation.profile.address.AddressViewModel
import com.softwaresekolah.inosoft.presentation.profile.personalData.PersonalDataScreen
import com.softwaresekolah.inosoft.presentation.profile.parentData.ParentDataScreen
import com.softwaresekolah.inosoft.presentation.profile.fileKelengkpanSiswa.FileKelengkapanSiswaScreen
import com.softwaresekolah.inosoft.presentation.profile.etc.EtcScreen
import com.softwaresekolah.inosoft.presentation.profile.etc.EtcViewModel
import com.softwaresekolah.inosoft.presentation.profile.parentData.ParentDataViewModel
import com.softwaresekolah.inosoft.presentation.profile.personalData.PersonalDataViewModel
import com.softwaresekolah.inosoft.presentation.profile.profile.ProfileScreen
import com.softwaresekolah.inosoft.presentation.settings.listAccount.ListAccountScreen
import com.softwaresekolah.inosoft.presentation.settings.setting.SettingViewModel
import com.softwaresekolah.inosoft.presentation.settings.setting.SettingsScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SsNavigator(
) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasUpdate = false,

            ),
            BottomNavItem(
                title = "Notification",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                hasUpdate = false,
                badgeCount = 5
            ),
            BottomNavItem(
                title = "Setting",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                hasUpdate = false,
            ),
            BottomNavItem(
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
                webViewUrl = "https://youtube.com"
            ),
            NavItem(
                title = "Rapor Bulanan",
                selectedIcon = Icons.Filled.Newspaper,
                unselectedIcon = Icons.Outlined.Newspaper,
                hasUpdate = false,
                badgeCount = 5,
                webViewUrl = "https://github.com"
            ),
            NavItem(
                title = "Rapor Mingguan",
                selectedIcon = Icons.Filled.Newspaper,
                unselectedIcon = Icons.Outlined.Newspaper,
                hasUpdate = false,
                webViewUrl = "https://stackoverflow.com"
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
        Route.ProfileScreen.route -> 3
        else -> 0
    }

    var selectedItemDrawer by rememberSaveable {
        mutableStateOf(999)
    }

    val isMainMenu = remember(key1 = backStackState) {
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
                onItemClick = { item ->
                    selectedItemDrawer = drawerNavigationItems.indexOf(item)
                    navigateToDrawDetails(
                        item = item,
                        navController = navController
                    )
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
                if (isMainMenu) {
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
                if (isMainMenu){
                    TopBar(
                        title = actionBarTitle,
                        scrollBehavior = scrollBehavior,
                        drawerState = drawerState,
                        multiSelectState = multipleSelectModeState,
                        notifSelectedItem = selectedItems
                    )
                }
            }
        ){
            val bottomPadding = it.calculateBottomPadding()
            val topPadding = it.calculateTopPadding()

//            if (isAccountBottomSheetOpen.value){
//                ModalBottomSheet(
//                    modifier = Modifier.fillMaxSize(),
//                    sheetState = accountBottomSheetState,
//                    containerColor = Color.White,
//                    onDismissRequest = { isAccountBottomSheetOpen.value = false }
//                ) {
//                    BottomSheetScaffold(
//                        scaffoldState = accountBottomSheetScaffoldState,
//                        sheetContent = {  },
//                        topBar = {
//                            Box (modifier = Modifier.fillMaxWidth(),
//                                contentAlignment = Alignment.CenterEnd
//                            ){
//                                Button(
//                                    modifier = Modifier
//                                        .padding(24.dp),
//                                    onClick = {
//                                        navController.navigate(Route.LoginScreen.route)
//                                        isAccountBottomSheetOpen.value = false
//                                    },
//                                    contentPadding = PaddingValues(horizontal = 30.dp),
//                                    shape = RoundedCornerShape(8.dp)
//                                ) {
//                                    Text(
//                                        text = "Tambah Akun",
//                                        fontSize = 12.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                }
//                            }
//                        },
//
//                    ) {
//                        val viewModel: ListAccountViewModel = hiltViewModel()
//                        val state = viewModel.state.value
//                        ListAccount(state = state, onEvent = viewModel::onEvent)
//                    }
//                }
//            }

            LaunchedEffect(sharedViewModel.redirectTo.value) {
                if (sharedViewModel.redirectTo.value.isNotEmpty()){
                    navigateToTab(navController = navController, route = sharedViewModel.redirectTo.value)
                }
            }

            NavHost(
                navController = navController,
                startDestination = Route.HomeScreen.route,
            ) {
                composable(route = Route.HomeScreen.route) { backStackEntry ->
                    HomeScreen(modifier = Modifier.padding(bottom = bottomPadding, top = topPadding), onNavigate = { sharedViewModel.onEvent(SharedViewModelEvent.UpdateData) })
                }
                composable(route = Route.NotificationScreen.route, deepLinks = listOf(navDeepLink {
                        uriPattern = "https://s.com/notification/{idSiswa}"
                    }),
                ) {
                    val idSiswa = navController.currentBackStackEntry?.arguments?.getString("idSiswa")
                    Timber.tag("NOTIFICATION ARGUMENT").d(idSiswa.toString())
                    NotificationScreen(
                        navController = navController,
                        state = multipleSelectModeState,
                        selectedItems = selectedItems,
                        modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
                    )
                }
                composable(route = Route.SettingScreen.route) {
                    val viewModel: SettingViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    SettingsScreen(
                        viewModel::onEvent,
                        navController = navController,
                        state = state,
                        isAccountBottomSheetOpen = isAccountBottomSheetOpen,
                        modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
                    )
                }
                composable(route = Route.ProfileScreen.route) {
                    ProfileScreen(navController = navController, modifier = Modifier.padding(bottom = bottomPadding, top = topPadding))
                }
                composable(route = Route.DrawDetailScreen.route){
                    navController.previousBackStackEntry?.savedStateHandle?.get<NavItem?>("drawItem")
                        ?.let { item ->
                            DrawDetailScreen(
                                item = item,
                                navigateUp = { navController.navigateUp() },
                            )
                        }
                }
                composable(route = Route.NotificationDetailScreen.route){
                    navController.previousBackStackEntry?.savedStateHandle?.get<Notification?>("notifItem")
                        ?.let { item ->
                            NotificationDetailScreen(
                                item = item,
                                navigateUp = { navController.navigateUp() },
                            )
                        }
                }
                composable(route = Route.DataDiriScreen.route) {
                    val viewModel: PersonalDataViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    PersonalDataScreen(navigateUp = { navController.navigateUp() }, onEvent = viewModel::onEvent, state = state)
                }

                composable(route = Route.AlamatScreen.route) {
                    val viewModel: AddressViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    AddressScreen(navigateUp = { navController.navigateUp() }, state = state, onEvent = viewModel::onEvent)
                }
                composable(route = Route.DataOrangTuaScreen.route) {
                    val viewModel: ParentDataViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    ParentDataScreen(navigateUp = { navController.navigateUp() }, state = state, onEvent = viewModel::onEvent)
                }
                composable(route = Route.FileKelengkapanSiswaScreen.route) {
                    FileKelengkapanSiswaScreen(navigateUp = { navController.navigateUp() })
                }
                composable(route = Route.LainnyaScreen.route) {
                    val viewModel: EtcViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    EtcScreen(navigateUp = { navController.navigateUp() }, state = state, onEvent = viewModel::onEvent)
                }
                composable(route = Route.LoginScreen.route){
                    val viewModel: LoginViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    LoginScreen(viewModel::onEvent, state, navController = navController)
                }
                composable(route = Route.LoginExpScreen.route){
                    val viewModel: LoginExpViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    LoginExpScreen(viewModel::onEvent, state, navController = navController)
                }
                composable(route = Route.ListAccountScreen.route) {
                    ListAccountScreen(navController = navController, navigateUp = { navController.navigateUp() })
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
//        navController.graph.startDestinationRoute?.let { screenRoute ->
//            popUpTo(screenRoute) {
//                saveState = true
//                inclusive = true
//            }
//        }
        popUpTo(navController.graph.id){
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun navigateToNotificationDetails(navController: NavController, item: Notification) {
    navController.currentBackStackEntry?.savedStateHandle?.set("notifItem", item)
    navController.navigate(
        route = Route.NotificationDetailScreen.route
    )
}

fun navigateTo(navController: NavController, route: String){
    navController.navigate(
        route = route
    )
}
private fun navigateToDrawDetails(navController: NavController, item: NavItem) {
    navController.currentBackStackEntry?.savedStateHandle?.set("drawItem", item)
    navController.navigate(
        route = Route.DrawDetailScreen.route
    )
}