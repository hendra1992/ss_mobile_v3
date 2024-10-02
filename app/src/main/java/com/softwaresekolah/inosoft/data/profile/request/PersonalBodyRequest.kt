package com.softwaresekolah.inosoft.data.profile.request

data class PersonalBodyRequest(
    val id_dep: String,
    val id_siswa: String,
    val siswa_email: String,
    val siswa_hp: String,
    val siswa_jenis_kelamin: String,
    val siswa_nama_panggilan: String,
    val siswa_no_akta_lahir: String,
    val siswa_no_whatsapp: String,
    val siswa_tanggal_lahir: String,
    val siswa_tempat_lahir: String
)