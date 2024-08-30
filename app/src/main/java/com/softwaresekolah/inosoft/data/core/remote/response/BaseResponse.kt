package com.softwaresekolah.inosoft.data.core.remote.response

data class BaseResponse<T>(
    val data: T?,
    val errors: String?,
    val messages: String,
    val request_id: String,
    val timestamp: String
)