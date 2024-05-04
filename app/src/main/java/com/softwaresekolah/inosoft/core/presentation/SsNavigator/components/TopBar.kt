package com.softwaresekolah.inosoft.core.presentation.SsNavigator.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.softwaresekolah.inosoft.notification.data.Notification
import com.softwaresekolah.inosoft.core.presentation.common.MultiSelectionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    multiSelectState: MultiSelectionState,
    notifSelectedItem: SnapshotStateList<Notification>
) {
    val scope = rememberCoroutineScope()
    CenterAlignedTopAppBar(
        title = { Text(text = title)},
        navigationIcon = {
            if (multiSelectState.isMultiSelectionModeEnabled){
                IconButton(onClick = {
                    multiSelectState.isMultiSelectionModeEnabled = false
                    notifSelectedItem.clear()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Cancel")
                }
            }else{
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (multiSelectState.isMultiSelectionModeEnabled){
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}