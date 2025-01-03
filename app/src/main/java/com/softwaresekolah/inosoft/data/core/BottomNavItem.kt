package com.softwaresekolah.inosoft.data.core

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.RawValue

class BottomNavItem (
    val title: String,
    val selectedIcon: @RawValue ImageVector,
    val unselectedIcon: @RawValue ImageVector,
    val hasUpdate: Boolean,
    var badgeCount: Int? = null
)