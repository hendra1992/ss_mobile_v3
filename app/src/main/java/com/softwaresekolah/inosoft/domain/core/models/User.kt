package com.softwaresekolah.inosoft.domain.core.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey val idSiswa: String,
    val imageUrl: String,
    val siswaNama: String,
    val accessToken: String,
    val refreshToken: String,
    val depkode: String,
    val username: String,
    val idDep: String,
    val userId: String,
    val fcmToken: String
) : Parcelable
