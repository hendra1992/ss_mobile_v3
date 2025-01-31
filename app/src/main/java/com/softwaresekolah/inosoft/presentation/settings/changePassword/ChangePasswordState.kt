package com.softwaresekolah.inosoft.presentation.settings.changePassword

data class ChangePasswordState (
    var isLoading: Boolean = false,
    var text: String? = null,
    var success: String? = null,
    var currentPassIsError: Boolean = false,
    var currentPassErrorText: String? = null,
    var newPassIsError: Boolean = false,
    var newPassErrorText: String? = null,
    var confNewPassIsError: Boolean = false,
    var confNewPassErrorText: String? = null,
)