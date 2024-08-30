package com.softwaresekolah.inosoft.presentation.settings.setting

sealed class SettingEvent {
    object logout: SettingEvent()
    object resetState: SettingEvent()
}