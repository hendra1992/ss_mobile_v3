package com.softwaresekolah.inosoft.presentation.auth.Login

import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody

sealed class LoginEvent {
    data class OnClickLogin(
        val depkode: String,
        val loginBody : LoginRequestBody
    ): LoginEvent()
    data object OnClearError: LoginEvent()
}