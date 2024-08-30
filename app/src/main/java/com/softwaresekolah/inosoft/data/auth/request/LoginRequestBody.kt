package com.softwaresekolah.inosoft.data.auth.request

data class LoginRequestBody(
    var siswa_username: String,
    var siswa_password: String,
    var device_token: String,
    var device_imei: String,
)