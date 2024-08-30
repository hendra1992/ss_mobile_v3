package com.softwaresekolah.inosoft.presentation.auth.expLogin

import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody

sealed class LoginExpEvent {
    data class OnClickLogin(
        val depkode: String,
        val loginBody : LoginRequestBody
    ): LoginExpEvent()
    data object OnClearError: LoginExpEvent()
}