package com.softwaresekolah.inosoft.presentation.core.SsNavigator.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModelEvent
import com.softwaresekolah.inosoft.presentation.core.common.MultiSelectionState
import com.softwaresekolah.inosoft.presentation.notification.list.NotificationListEvent
import com.softwaresekolah.inosoft.presentation.notification.list.component.NotificationDialog
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    multiSelectState: MultiSelectionState,
    notifSelectedItem: SnapshotStateList<NotificationListResponse>,
    onEvent: (NotificationListEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val isNotificationDialogShow = remember {
        mutableStateOf(false)
    }

    if (isNotificationDialogShow.value){
        NotificationDialog(action = { onEvent(NotificationListEvent.DeleteBatchNotification(notifSelectedItem.toList())) }, isNotificationDialogShow = isNotificationDialogShow, text = "Apakah Anda Yakin Mau Menghapus Pemberitahuan Yang Terpilih?")
    }
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
                IconButton(onClick = {
                    isNotificationDialogShow.value = true
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}