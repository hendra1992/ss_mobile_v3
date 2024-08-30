package com.softwaresekolah.inosoft.domain.auth.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.data.auth.request.RefreshTokenRequestBody
import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.auth.response.RefreshTokenDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.AuthApiService
import com.softwaresekolah.inosoft.data.core.remote.services.RefreshTokenService
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import retrofit2.Response
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    val refreshTokenApiService: RefreshTokenService,
    val localManager: LocalManager
) {
    suspend operator fun invoke(body: RefreshTokenRequestBody, ): ApiResponse<BaseResponse<RefreshTokenDataResponse>> {
        return refreshTokenApiService.refreshToken(body = body)
    }
}