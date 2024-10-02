package com.softwaresekolah.inosoft.data.profile.request

data class EtcDataBodyRequest(
    val id_dep: String,
    val id_siswa: String,
    val siswa_agama: Int,
    val siswa_gol_darah: String,
    val siswa_warganegara: String
)