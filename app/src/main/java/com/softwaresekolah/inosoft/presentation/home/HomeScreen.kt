package com.softwaresekolah.inosoft.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModel
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModelEvent
import com.softwaresekolah.inosoft.presentation.home.component.WebView
import com.softwaresekolah.inosoft.util.NetworkMonitor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    val context = LocalContext.current
    val networkMonitor = NetworkMonitor(context)

    val isNetworkAvailable = remember {
        mutableStateOf(networkMonitor.isNetworkAvailable())
    }

    SideEffect {
        onNavigate()
    }

    if (isNetworkAvailable.value){
        val url = "https://www.google.com"
        Box(modifier = modifier){
            WebView(url)
        }
    }else{
        Box(modifier = modifier){
            Text(text = "no internet")
        }
    }

}