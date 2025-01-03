package com.softwaresekolah.inosoft.presentation.notification.list

import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse

sealed class NotificationListEvent {
    data object OnUpdate: NotificationListEvent()
    data object OnClearText: NotificationListEvent()
    data object OnClearError: NotificationListEvent()
    data object OnDeleteAllRead: NotificationListEvent()
    data object OnReadAll: NotificationListEvent()
    data class DeleteBatchNotification(
        var notificationIds: List<NotificationListResponse> = emptyList()
    ): NotificationListEvent()
}