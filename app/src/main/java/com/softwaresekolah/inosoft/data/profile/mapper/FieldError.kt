package com.softwaresekolah.inosoft.data.profile.mapper

data class FieldError(
    val field: String,
    val message: String,
    val client_message: String,
)