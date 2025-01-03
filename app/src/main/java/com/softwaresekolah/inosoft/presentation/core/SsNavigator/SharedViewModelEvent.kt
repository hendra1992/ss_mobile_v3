package com.softwaresekolah.inosoft.presentation.core.SsNavigator

import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse

sealed class SharedViewModelEvent {
    data object UpdateData: SharedViewModelEvent()
    data object OnCLearText: SharedViewModelEvent()


}