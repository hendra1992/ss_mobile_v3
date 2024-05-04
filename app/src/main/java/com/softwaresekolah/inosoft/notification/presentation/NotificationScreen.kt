package com.softwaresekolah.inosoft.notification.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.softwaresekolah.inosoft.notification.data.Notification
import com.softwaresekolah.inosoft.core.presentation.common.MultiSelectList
import com.softwaresekolah.inosoft.core.presentation.common.MultiSelectionState
import com.softwaresekolah.inosoft.notification.presentation.component.NotificationItem


@Composable
fun NotificationScreen(
    state: MultiSelectionState,
    selectedItems: SnapshotStateList<Notification>
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
                    body = "isi pengumuman sekolah",
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
                  Toast.makeText(context, "Click $it", Toast.LENGTH_SHORT).show()
              }
        },
    )
}