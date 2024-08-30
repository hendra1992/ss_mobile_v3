package com.softwaresekolah.inosoft.data.core.remote.services

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.auth.request.RefreshTokenRequestBody
import com.softwaresekolah.inosoft.data.auth.response.RefreshTokenDataResponse
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("config")
    suspend fun getConfig(
        @Body body: ConfigRequestBody
    ): ApiResponse<BaseResponse<ConfigDataResponse>>
}