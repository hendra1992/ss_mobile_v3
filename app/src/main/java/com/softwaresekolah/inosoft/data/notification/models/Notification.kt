package com.softwaresekolah.inosoft.data.notification.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val id : Int,
    val type: String,
    val title: String,
    val body: String,
    val date: String,
    val unRead: Boolean,
) : Parcelable