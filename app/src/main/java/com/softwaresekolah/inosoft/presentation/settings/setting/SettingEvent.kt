package com.softwaresekolah.inosoft.presentation.settings.setting

import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse

sealed class SettingEvent {
    object logout: SettingEvent()
    data object OnUpdate: SettingEvent()
    data object OnClearText: SettingEvent()
    object resetState: SettingEvent()
    data class OnSave(
        val setting: GetSettingResponse,
    ): SettingEvent()
}