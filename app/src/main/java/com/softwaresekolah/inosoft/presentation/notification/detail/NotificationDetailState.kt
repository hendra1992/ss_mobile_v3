package com.softwaresekolah.inosoft.presentation.notification.detail

import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse

class NotificationDetailState (
    var isLoading: Boolean = false,
    var text: String? = null,
    var success: String? = null
)