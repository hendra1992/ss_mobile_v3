package com.softwaresekolah.inosoft.core.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.softwaresekolah.inosoft.core.presentation.common.SystemBarsColorChanger

private val DarkColorScheme = darkColorScheme(
    background = Black,
    primary = Blue,
    error = DarkRed,
    surface = LightBlack
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    primary = Blue,
    error = LightRed,
    surface = Color.White
)


@Composable
fun SsV3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SystemBarsColorChanger(
        statusColor = colorScheme.primary,
        navigationBarColor = colorScheme.primary,
        isLightIcon = darkTheme
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}