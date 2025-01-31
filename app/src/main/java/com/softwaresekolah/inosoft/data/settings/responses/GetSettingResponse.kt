package com.softwaresekolah.inosoft.data.settings.responses

data class GetSettingResponse(
    val siswa_notif_alpa: Boolean,
    val siswa_notif_bayar: Boolean,
    val siswa_notif_telat: Boolean
)