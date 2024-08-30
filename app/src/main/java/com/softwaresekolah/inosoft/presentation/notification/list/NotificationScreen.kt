package com.softwaresekolah.inosoft.presentation.notification.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateToNotificationDetails
import com.softwaresekolah.inosoft.data.notification.Notification
import com.softwaresekolah.inosoft.presentation.core.common.MultiSelectList
import com.softwaresekolah.inosoft.presentation.core.common.MultiSelectionState
import com.softwaresekolah.inosoft.presentation.notification.list.component.NotificationItem


@Composable
fun NotificationScreen(
    navController: NavController,
    state: MultiSelectionState,
    selectedItems: SnapshotStateList<Notification>,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = state.isMultiSelectionModeEnabled, key2 = selectedItems.size) {
        if  (state.isMultiSelectionModeEnabled && selectedItems.isEmpty()){
            state.isMultiSelectionModeEnabled = false
        }
    }
    val list = mutableListOf<Notification>()

    for (i in 1..5){
        if (i % 2 == 0){
            list.add(
                Notification(
                    id  = i,
                    title = "Pemberitahuan Pembayaran",
                    body = "detail",
                    date = "13:30",
                    type = "pemberitahuan",
                    unRead = false
                )
            )
        }else{
            list.add(
                Notification(
                    id = i,
                    title = "Pengumuman Sekolah",
                    body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et ornare risus, non euismod libero. Integer porta ornare nisl, interdum interdum justo mollis et. Pellentesque ut felis egestas, lacinia quam nec, egestas tortor. Nullam convallis ut tellus eget feugiat. Sed vitae metus vel nunc sagittis dictum sit amet a mi. Sed volutpat convallis quam a porttitor. Aenean magna dolor, bibendum quis tempus nec, sodales eget turpis. Donec nec varius augue.",
                    date = "kemarin",
                    type = "pengumuman",
                    unRead = true
                )
            )
        }
    }
    val context = LocalContext.current
    MultiSelectList(
        state = state,
        items = list,
        modifier = modifier,
        selectedItems = selectedItems,
        itemContent = {
           NotificationItem(selectedItems.contains(it), it)
        },
        key = {
              it.id
        },
        onClick = {
              if (state.isMultiSelectionModeEnabled){
                  if (selectedItems.contains(it)){
                      selectedItems.remove(it)
                  }else{
                      selectedItems.add(it)
                  }
              }else{
                  navigateToNotificationDetails(navController, it)
              }
        },
    )
}

