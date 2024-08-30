package com.softwaresekolah.inosoft.data.core.remote.response

data class ConfigDataResponse(
    val akt_tahun_ajaran: String,
    val dep_enable_payment_gateway: String,
    val dep_enable_wallet: String,
    val dep_force_siswa_email_verified: String,
    val dep_id: String,
    val dep_kode: String,
    val dep_member_tipe: String,
    val dep_nama: String,
    val dep_tingkat_pendidikan: String,
    val dep_url: String,
    val foto_path: String,
    val id_akt: Int,
    val id_kelas: String,
    val id_sem: Int,
    val id_siswa: String,
    val is_available_siswa_email: Boolean,
    val is_verified_siswa_email: Boolean,
    val kelas_nama: String,
    val siswa_email: String,
    val siswa_email_verified_at: String,
    val siswa_foto: String,
    val siswa_nama: String,
    val siswa_nis: String,
    val siswa_pin: String
)