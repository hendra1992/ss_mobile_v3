package com.softwaresekolah.inosoft.domain.auth.interceptor

import com.softwaresekolah.inosoft.util.Constant
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val manager: LocalManager,
) : Interceptor {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
        const val DEPKODE_HEADER = "dep_kode"
        const val API_CLIENT_HEADER = "api_client_key"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            manager.getAccessJwt()
        }
        val depkode = runBlocking {
            manager.getDepkode()
        }
        val idSiswa = runBlocking {
            manager.getIdSiswa()
        }

        val request = chain.request().newBuilder()
        request.addHeader(HEADER_AUTHORIZATION, value = "$TOKEN_TYPE $token")
        request.addHeader(DEPKODE_HEADER, value = "$depkode")
        request.addHeader(API_CLIENT_HEADER, value = Constant.API_CLIENT_KEY)
        return chain.proceed(request.build())
    }
}