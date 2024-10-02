package com.softwaresekolah.inosoft.data.profile.mapper

data class CustomProfileError(
    val data: Any? = null,
    val errors: List<FieldError> = emptyList(),
    val request_id: String,
    val timestamp: String
)