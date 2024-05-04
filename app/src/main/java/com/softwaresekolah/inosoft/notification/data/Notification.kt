package com.softwaresekolah.inosoft.notification.data

data class Notification(
    val id : Int,
    val type: String,
    val title: String,
    val body: String,
    val date: String,
    val unRead: Boolean,
)