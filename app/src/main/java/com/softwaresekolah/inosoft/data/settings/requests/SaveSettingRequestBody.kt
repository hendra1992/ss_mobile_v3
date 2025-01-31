package com.softwaresekolah.inosoft.data.settings.requests

data class SaveSettingRequestBody(
    val id_dep: String,
    val id_siswa: String,
    val siswa_notif_alpa: Boolean,
    val siswa_notif_bayar: Boolean,
    val siswa_notif_telat: Boolean
)