package com.softwaresekolah.inosoft.presentation.profile.personalData

import com.softwaresekolah.inosoft.data.profile.response.PersonalDataResponse

data class PersonalDataState(
    var isLoading: Boolean = false,
    var personalData: PersonalDataResponse? = null,
    var text: String? = null,
    var success: String? = null,
    var nicknameIsError: Boolean = false,
    var emailIsError: Boolean = false,
    var birthCertificateNumberIsError: Boolean = false,
    var hpIsError: Boolean = false,
    var genderIsError: Boolean = false,
    var birthDateIsError: Boolean = false,
    var birthPlaceIsError: Boolean = false,
    var waIsError: Boolean = false,
    var nicknameErrorText: String? = null,
    var emailErrorText: String? = null,
    var birthCertificateNumberErrorText: String? = null,
    var hpErrorText: String? = null,
    var genderErrorText: String? = null,
    var birthDateErrorText: String? = null,
    var birthPlaceErrorText: String? = null,
    var waErrorText: String? = null,
)