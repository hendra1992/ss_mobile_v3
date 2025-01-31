package com.softwaresekolah.inosoft.data.notification.requests

data class ReadBatchBodyRequest (
    val id_dep: String,
    val id_siswa: String,
    val list_id_notif: List<String>
)