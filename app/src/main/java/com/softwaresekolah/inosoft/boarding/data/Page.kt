package com.softwaresekolah.inosoft.boarding.data

import androidx.annotation.DrawableRes
import com.softwaresekolah.inosoft.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        description = "Mudahnya pembayaran sekolah melalui yang dapat di lakukan dimanapun dan kapanpun",
        title = "Payment Gateway",
        image = R.drawable.walk_1
    ),
    Page(
        description =  "Lihat kembali bukti pembayaran anda dengan mudah melalui laporan pembayaran",
        title = "Laporan Pembayaran",
        image = R.drawable.walk_2
    ),
    Page(
        description = "Pantau kehadiran putra-putri anda di sekolah melalui pemberitahuan aplikasi dan halaman" +
                " data absensi",
        title = "Absensi",
        image = R.drawable.walk_3
    ),
)