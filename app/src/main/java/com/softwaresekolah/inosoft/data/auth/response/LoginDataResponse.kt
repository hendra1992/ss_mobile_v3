package com.softwaresekolah.inosoft.data.auth.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginDataResponse(
    val auth_token: String,
    val expires_in: Int,
    val id_dep: String,
    val id_rol: Int,
    val profile_photo_path: String,
    val id_siswa: String,
    val refresh_token: String,
    val siswa_nama: String,
    val siswa_nis: String,
    val usr_id: Int,
    val usr_name: String
) : Parcelable
