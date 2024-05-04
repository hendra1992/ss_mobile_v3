package com.softwaresekolah.inosoft.home.presentation

import androidx.compose.runtime.Composable
import com.softwaresekolah.inosoft.home.presentation.component.WebView

@Composable
fun HomeScreen() {
    val url = "https://www.google.com"
    WebView(url)
}