package com.softwaresekolah.inosoft.domain.auth.usecase

import android.util.Log
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.data.auth.request.LogoutBodyRequest
import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.core.mapper.ErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.AuthApiService
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val localManager: LocalManager,
    private val userRepository: UserRepository,
    private val authApiService: AuthApiService,
){
    suspend operator fun invoke(idSiswa: String) : Boolean{
        val softwareID = runBlocking {
            localManager.getSoftwareId()
        }
        val currentIdSiswa = runBlocking{
            localManager.getIdSiswa()
        }
        val user = runBlocking {
            userRepository.getUser(idSiswa)
        }

        user?.let {
            runBlocking {
                authApiService.logout(depkode = it.depkode, body = LogoutBodyRequest(
                usr_id = user.userId.toInt(),
                id_siswa = user.idSiswa,
                device_imei = softwareID.toString(),
                id_dep = user.idDep
                )).onSuccess {

                }.onError(ErrorEnvelopeMapper) {
                    val code = this.code
                    val message = this.message
                    val errorMessage = this.body.messages
                    Timber.tag("Logout Use Case").d("code : $code, message: $message, server message: $errorMessage")
                }
            }

            userRepository.deleteUser(it)
        }

        val users = runBlocking {
            userRepository.getUsers().first()
        }

        if (users.isNotEmpty()){
            if (currentIdSiswa.toString() == idSiswa){
                localManager.saveDepkode(users[0].depkode)
                localManager.saveAccessJwt(users[0].accessToken)
                localManager.saveRefreshJwt(users[0].refreshToken)
                localManager.saveIdDep(users[0].idDep)
                localManager.saveUserId(users[0].userId)
                localManager.saveIdSiswa(users[0].idSiswa)
            }
        }else{
            localManager.removeUserLogin()
            localManager.clearAllTokens()
        }
        return true
    }
}