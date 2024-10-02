package com.softwaresekolah.inosoft.data.profile.response

data class AddressDataResponse(
    val siswa_alamat: String,
    val siswa_kodepos: String,
    val siswa_kota: Int,
    val siswa_propinsi: Int,
    val siswa_telp: String
)