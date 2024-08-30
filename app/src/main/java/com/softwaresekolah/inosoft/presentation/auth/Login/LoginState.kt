package com.softwaresekolah.inosoft.presentation.auth.Login

data class LoginState(
    var isLoading: Boolean = false,
    var error: String? = null,
    var isMultipleAcc: Boolean = false,

)
