package com.softwaresekolah.inosoft.domain.auth.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.AuthApiService
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val authApiService: AuthApiService,
    val localManager: LocalManager
){
    suspend operator fun invoke(body: LoginRequestBody, depkode: String): ApiResponse<BaseResponse<LoginDataResponse>> {
        val fcmToken = runBlocking {
            localManager.readUserFCMToken().first()
        }
        body.device_token = fcmToken
        localManager.saveSoftwareId(body.device_imei)
        return authApiService.login(depkode = depkode, body = body)
    }
}