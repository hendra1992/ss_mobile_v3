package com.softwaresekolah.inosoft.domain.core.repository

import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.domain.core.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun upsertUser(user: User)

    suspend fun deleteUser(user: User)

    fun getUsers(): Flow<List<User>>
    suspend fun getUser(studentId: String): User?
    suspend fun getUserByUsername(username: String): User?

}