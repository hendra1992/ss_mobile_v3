package com.softwaresekolah.inosoft.presentation.profile.personalData

sealed class PersonalDataEvent {
    data object OnUpdate: PersonalDataEvent()
    data object OnClearText: PersonalDataEvent()
    data object OnClearError: PersonalDataEvent()
    data class OnSave(
        val nickname: String,
        val email: String,
        val birthPlace: String,
        val birthDate: String,
        val gender: String,
        val hp: String,
        val wa: String,
        val birthCertificateNumber: String,
    ): PersonalDataEvent()
}