package com.softwaresekolah.inosoft.core.presentation.SsNavigator.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.softwaresekolah.inosoft.core.data.NavItem
import kotlinx.coroutines.launch

@Composable
fun NavDraw(
    items: List<NavItem>,
    selectedItem: Int,
    onItemClick: (String) -> Unit,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {
        items.forEachIndexed{ index, item ->
            NavigationDrawerItem(
                label = { Text(text = item.title)},
                selected = selectedItem == index ,
                onClick = { 
                    onItemClick(item.title)
                    scope.launch {
                        drawerState.close()
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItem)
                            item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                badge = {
                    Text(text = if (item.badgeCount != null) item.badgeCount.toString()
                        else "")
                }
            )
        }
    }
}