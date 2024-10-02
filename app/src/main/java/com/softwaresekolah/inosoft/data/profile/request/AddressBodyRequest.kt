package com.softwaresekolah.inosoft.data.profile.request

data class AddressBodyRequest(
    val id_dep: String,
    val id_siswa: String,
    val siswa_alamat: String,
    val siswa_kodepos: String,
    val siswa_kota: Int,
    val siswa_propinsi: Int,
    val siswa_telp: String
)