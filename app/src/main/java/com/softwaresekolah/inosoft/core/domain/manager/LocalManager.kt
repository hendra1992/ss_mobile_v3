package com.softwaresekolah.inosoft.core.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>

    suspend fun saveUserLogin()
    fun readUserLogin(): Flow<Boolean>
    suspend fun removeUserLogin()
}