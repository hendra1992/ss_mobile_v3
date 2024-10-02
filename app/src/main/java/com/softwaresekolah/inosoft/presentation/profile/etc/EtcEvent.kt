package com.softwaresekolah.inosoft.presentation.profile.etc

sealed class EtcEvent {
    data object OnUpdate: EtcEvent()
    data object OnClearText: EtcEvent()
    data class OnSave(
        val goldar: String,
        val kwn: String,
        val agm: String
    ): EtcEvent()
}