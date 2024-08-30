package com.softwaresekolah.inosoft.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModel
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModelEvent
import com.softwaresekolah.inosoft.presentation.home.component.WebView

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    SideEffect {
        onNavigate()
    }
    val url = "https://www.google.com"
    Box(modifier = modifier){
        WebView(url)
    }
}