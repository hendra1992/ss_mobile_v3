package com.softwaresekolah.inosoft.presentation.core.SsNavigator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.data.core.NavItem
import kotlinx.coroutines.launch

@Composable
fun NavDraw(
    items: List<NavItem>,
    selectedItem: Int,
    onItemClick: (NavItem) -> Unit,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {
        items.forEachIndexed{ index, item ->
            NavigationDrawerItem(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp).padding(horizontal = 16.dp),
                label = { Text(text = item.title)},
                selected = selectedItem == index ,
                onClick = { 
                    onItemClick(item)
                    scope.launch {
                        drawerState.close()
                    }
                },
                shape = RoundedCornerShape(8.dp),
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