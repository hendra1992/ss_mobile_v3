package com.softwaresekolah.inosoft.data.core

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NavItem(
    val title: String,
    val selectedIcon: @RawValue ImageVector,
    val unselectedIcon: @RawValue ImageVector,
    val hasUpdate: Boolean,
    val webViewUrl: String,
    val badgeCount: Int? = null
) : Parcelable
