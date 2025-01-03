package com.softwaresekolah.inosoft.presentation.notification.list

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateToNotificationDetails
import com.softwaresekolah.inosoft.data.notification.models.Notification
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedState
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModelEvent
import com.softwaresekolah.inosoft.presentation.core.common.MultiSelectList
import com.softwaresekolah.inosoft.presentation.core.common.MultiSelectionState
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.notification.list.component.NotificationItem
import com.softwaresekolah.inosoft.util.UIComponent
import timber.log.Timber


@Composable
fun NotificationScreen(
    navController: NavController,
    state: MultiSelectionState,
    selectedItems: SnapshotStateList<NotificationListResponse>,
    modifier: Modifier = Modifier,
    listState: NotificationListState,
    onEvent: (NotificationListEvent) -> Unit,
) {
    if (listState.isLoading){
         SimpleLoadingScreen()
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = state.isMultiSelectionModeEnabled, key2 = selectedItems.size) {
        if  (state.isMultiSelectionModeEnabled && selectedItems.isEmpty()){
            state.isMultiSelectionModeEnabled = false
        }
    }

    LaunchedEffect(Unit) {
        onEvent(NotificationListEvent.OnUpdate)
    }

    LaunchedEffect(listState.success) {
        if (state.isMultiSelectionModeEnabled){
            state.isMultiSelectionModeEnabled = false
        }
        if (listState.success != null && listState.success != ""){
            Toast.makeText(context, listState.success, Toast.LENGTH_SHORT).show()
            onEvent(NotificationListEvent.OnClearText)
            onEvent(NotificationListEvent.OnUpdate)
        }
    }

    MultiSelectList(
        state = state,
        items = listState.notificationList,
        modifier = modifier,
        selectedItems = selectedItems,
        itemContent = {
           NotificationItem(selectedItems.contains(it), it)
        },
        key = {
              it.notif_id
        },
        onClick = {
              if (state.isMultiSelectionModeEnabled){
                  if (selectedItems.contains( it)){
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

