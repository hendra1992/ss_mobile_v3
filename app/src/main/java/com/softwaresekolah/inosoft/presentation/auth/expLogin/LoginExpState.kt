package com.softwaresekolah.inosoft.presentation.auth.expLogin

import com.softwaresekolah.inosoft.domain.core.models.User

data class LoginExpState(
    var isLoading: Boolean = false,
    var error: String? = null,
    var isSuccess: Boolean = false,
    var user: User? = null,
)
