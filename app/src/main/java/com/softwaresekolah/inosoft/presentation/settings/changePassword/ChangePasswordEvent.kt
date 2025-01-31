package com.softwaresekolah.inosoft.presentation.settings.changePassword

sealed class ChangePasswordEvent {
    data object OnClearText: ChangePasswordEvent()
    data object OnClearError: ChangePasswordEvent()
    data class OnSubmit(
        val currentPass: String,
        val newPass: String,
        val confNewPass: String,
    ): ChangePasswordEvent()
}