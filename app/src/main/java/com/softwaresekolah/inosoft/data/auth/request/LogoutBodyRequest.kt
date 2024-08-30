package com.softwaresekolah.inosoft.data.auth.request

data class LogoutBodyRequest(
    val device_imei: String,
    val id_dep: String,
    val id_siswa: String,
    val usr_id: Int
)