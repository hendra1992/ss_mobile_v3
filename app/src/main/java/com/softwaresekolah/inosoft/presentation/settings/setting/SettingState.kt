package com.softwaresekolah.inosoft.presentation.settings.setting

import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse

data class SettingState(
    var logoutSuccess: Boolean = false,
    var isLoading: Boolean = false,
    var settings: GetSettingResponse? = null,
    var text: String? = null,
    var success: String? = null
)
