package com.softwaresekolah.inosoft.data.notification.responses

data class NotificationDetailResponse(
    val date_order: String,
    val id_akt: Int,
    val id_dep: String,
    val id_sem: Int,
    val id_siswa: String,
    val notif_content: String,
    val notif_created: String,
    val notif_id: String,
    val notif_jenis: String,
    val notif_read_status: Boolean,
    val notif_subtitle: String,
    val notif_title: String,
    val wajib_tipe_bayar: String
)