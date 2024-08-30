package com.softwaresekolah.inosoft.presentation.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.softwaresekolah.inosoft.data.core.NavItem
import com.softwaresekolah.inosoft.presentation.drawer.component.DrawTopBar
import com.softwaresekolah.inosoft.presentation.drawer.component.DrawWebView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawDetailScreen(
    item: NavItem,
    navigateUp: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        topBar = { DrawTopBar(title = item.title, scrollBehavior = scrollBehavior, navigateUp = navigateUp) }
    ){padding ->
        Box (modifier = Modifier.padding(padding)){
            DrawWebView(url = item.webViewUrl)
        }
    }
}