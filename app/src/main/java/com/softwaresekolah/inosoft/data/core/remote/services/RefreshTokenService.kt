package com.softwaresekolah.inosoft.data.core.remote.services

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.auth.request.RefreshTokenRequestBody
import com.softwaresekolah.inosoft.data.auth.response.RefreshTokenDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("auth/refresh_token")
    suspend fun refreshToken(
        @Body body: RefreshTokenRequestBody
    ): ApiResponse<BaseResponse<RefreshTokenDataResponse>>
}