package com.softwaresekolah.inosoft.core.data

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasUpdate: Boolean,
    val badgeCount: Int? = null
)
