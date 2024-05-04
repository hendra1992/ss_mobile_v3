package com.softwaresekolah.inosoft.core.presentation.common

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SystemBarsColorChanger(
    statusColor: Color?,
    navigationBarColor: Color?,
    isLightIcon: Boolean
) {
    val window = (LocalContext.current as Activity).window
    val view = LocalView.current


    SideEffect {
        statusColor?.let {
            window.statusBarColor = it.toArgb()
        }

        navigationBarColor?.let {
            window.navigationBarColor = it.toArgb()
        }

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isLightIcon
    }
}