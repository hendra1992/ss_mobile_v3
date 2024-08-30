package com.softwaresekolah.inosoft.presentation.drawer.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrawTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
                IconButton(onClick = navigateUp
                ) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Cancel")
                }

        },
        scrollBehavior = scrollBehavior
    )
}