package com.softwaresekolah.inosoft.data.profile.request

data class ParentDataBodyRequest(
    val id_dep: String,
    val id_siswa: String,
    val ortu_ayah_hp: String,
    val ortu_ayah_nama: String,
    val ortu_ibu_hp: String,
    val ortu_ibu_nama: String,
)