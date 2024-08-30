package com.softwaresekolah.inosoft.data.core.repository

import com.softwaresekolah.inosoft.data.core.local.UserDao
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun upsertUser(user: User) {
        userDao.upsert(user = user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(user = user)
    }

    override fun getUsers(): Flow<List<User>> {
        return userDao.getUsers()
    }

    override suspend fun getUser(idSiswa: String): User? {
        return userDao.getUser(idSiswa = idSiswa)
    }

}