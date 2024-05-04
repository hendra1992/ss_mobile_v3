package com.softwaresekolah.inosoft.core.presentation.SsNavigator.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.softwaresekolah.inosoft.core.data.NavItem

@Composable
fun BottomNavBar(
    items: List<NavItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed{ index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                label = { Text(text = item.title)},
                icon = { 
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null){
                                Badge{
                                    Text(text = item.badgeCount.toString())
                                }
                            }else if(item.hasUpdate){
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(index == selectedItem) 
                                item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
            
        }
    }
}