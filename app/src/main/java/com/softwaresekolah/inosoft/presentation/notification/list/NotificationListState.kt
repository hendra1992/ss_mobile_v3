package com.softwaresekolah.inosoft.presentation.notification.list

import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse

data class NotificationListState (
    var isLoading: Boolean = false,
    var notificationList: List<NotificationListResponse> = emptyList(),
    val notificationUnread: Int? = null,
    var text: String? = null,
    var success: String? = null
)