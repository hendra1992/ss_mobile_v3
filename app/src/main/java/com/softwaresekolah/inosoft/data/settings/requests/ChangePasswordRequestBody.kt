package com.softwaresekolah.inosoft.data.settings.requests

data class ChangePasswordRequestBody(
    val confirm_new_password: String,
    val id_siswa: String,
    val id_dep: String,
    val new_password: String,
    val old_password: String
)