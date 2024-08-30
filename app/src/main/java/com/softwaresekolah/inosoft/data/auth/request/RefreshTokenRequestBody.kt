package com.softwaresekolah.inosoft.data.auth.request

data class RefreshTokenRequestBody(
    val usr_id: Int,
    var refresh_token: String
)
