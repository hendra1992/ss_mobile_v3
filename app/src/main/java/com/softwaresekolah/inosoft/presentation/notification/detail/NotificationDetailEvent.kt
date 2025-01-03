package com.softwaresekolah.inosoft.presentation.notification.detail

sealed class NotificationDetailEvent {
     data class OnRead(
        val notificationId: String,
    ): NotificationDetailEvent()
}