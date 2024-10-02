package com.softwaresekolah.inosoft.data.core.remote.services

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.util.Constant
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.data.auth.request.LogoutBodyRequest
import com.softwaresekolah.inosoft.data.auth.request.RefreshTokenRequestBody
import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.auth.response.RefreshTokenDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/siswa")
    suspend fun login(
        @Header("Dep-Kode") depkode: String,
        @Header("Api-Client-Key") apiClientKey: String = Constant.API_CLIENT_KEY,
        @Body body: LoginRequestBody,
    ): ApiResponse<BaseResponse<LoginDataResponse>>

     @POST("auth/logout")
    suspend fun logout(
        @Header("Dep-Kode") depkode: String,
        @Header("Api-Client-Key") apiClientKey: String = Constant.API_CLIENT_KEY,
        @Body body: LogoutBodyRequest,
    ): ApiResponse<BaseResponse<LoginDataResponse>>
}