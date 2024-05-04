package com.softwaresekolah.inosoft.core.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.softwaresekolah.inosoft.core.presentation.theme.Blue

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box (
        modifier = modifier.background(brush = Brush.verticalGradient(
            listOf(
                Color.White, Blue
            )
        ))
    ){
        content()
    }
}