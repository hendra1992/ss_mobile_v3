package com.softwaresekolah.inosoft.presentation.settings.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingMenuTopbar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit,
    actionVisibility: Boolean = false,
) {

    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = navigateUp
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Cancel")
            }

        },
        actions = {
            if (actionVisibility){
//                IconButton(onClick = { }) {
//                    Icon(imageVector = Icons.Default.Save, contentDescription = "save")
//                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}