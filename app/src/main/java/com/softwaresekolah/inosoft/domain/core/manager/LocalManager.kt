package com.softwaresekolah.inosoft.domain.core.manager

import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.auth.response.RefreshTokenDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LocalManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
        suspend fun saveAppExp()
    fun readAppExp(): Flow<Boolean>
    suspend fun saveUserLogin()
    fun readUserFCMToken(): Flow<String>
    suspend fun saveUserFCMToken(token: String)
    fun readUserLogin(): Flow<Boolean>
    suspend fun saveSoftwareId(id: String)
    suspend fun getSoftwareId(): String?
    suspend fun removeUserLogin()
    suspend fun saveAccessJwt(token: String)
    suspend fun saveRefreshJwt(token: String)
    suspend fun saveUserId(userId: String)
    suspend fun saveDepkode(depkode: String)
    suspend fun saveIdDep(idDep: String)
    suspend fun saveIdSiswa(idSiswa: String)
    suspend fun getUserId(): String?
    suspend fun getIdDep(): String?
    suspend fun getIdSiswa(): String?
    suspend fun getDepkode(): String?
    suspend fun getAccessJwt(): String?
    suspend fun getRefreshJwt(): String?
    suspend fun refreshToken(): String?
    suspend fun clearAllTokens()
}