package com.softwaresekolah.inosoft.auth.presentation.Login

sealed class LoginEvent {
    object onClickLogin: LoginEvent()
}