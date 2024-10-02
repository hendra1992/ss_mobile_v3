package com.softwaresekolah.inosoft.domain.auth.usecase

import android.app.Application
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.softwaresekolah.inosoft.data.auth.request.RefreshTokenRequestBody
import com.softwaresekolah.inosoft.data.core.mapper.ErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.core.remote.services.RefreshTokenService
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.presentation.core.MainActivity.MainActivity
import com.softwaresekolah.inosoft.util.Constant
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject


@Suppress("IMPLICIT_CAST_TO_ANY")
class AuthAuthenticator @Inject constructor(
    private val tokenManager: LocalManager,
    private val userRepository: UserRepository,
    private val refreshTokenService: RefreshTokenService,
    private val logoutUseCase: LogoutUseCase,
    private val application: Application,

    ) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
        const val DEPKODE_HEADER = "dep_kode"
        const val API_CLIENT_HEADER = "api_client_key"
    }
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            tokenManager.getAccessJwt()
        }
        synchronized(this) {
            val updatedToken = runBlocking {
                tokenManager.getAccessJwt()
            }
            val refreshToken = runBlocking {
                tokenManager.getRefreshJwt()
            }
            val userId = runBlocking {
                tokenManager.getUserId()
            }

            val depkode = runBlocking {
                tokenManager.getDepkode()
            }

            var token: String? = null

            if (currentToken != updatedToken) token = updatedToken else {

                    val newSessionResponse = runBlocking { refreshTokenService.refreshToken(
                        body = RefreshTokenRequestBody(refresh_token = refreshToken.toString(), usr_id = userId.toString().toInt())
                    )}

                    newSessionResponse.onSuccess {
                        data.data?.let {newToken ->
                            runBlocking {
                                tokenManager.saveAccessJwt(newToken.auth_token)
                                tokenManager.saveRefreshJwt(newToken.refresh_token)
                            }
                            token = newToken.auth_token
                        }
                    }.onError(ErrorEnvelopeMapper) {
                        if (this.code == 401) {
                            runBlocking {
                                tokenManager.saveAppExp()
                            }
                            token = null
                        }
                    }.onException {
                        Timber.tag("AUTHENTICATOR").d(message)
                        token = null
                    }
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .addHeader(DEPKODE_HEADER, value = "$depkode")
                .addHeader(API_CLIENT_HEADER, value = Constant.API_CLIENT_KEY)
                .build() else null
        }
    }
}