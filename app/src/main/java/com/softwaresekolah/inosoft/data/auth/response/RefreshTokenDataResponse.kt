package com.softwaresekolah.inosoft.data.auth.response

data class RefreshTokenDataResponse(
    val expires_in: Int,
    val auth_token: String,
    val refresh_token: String,
)