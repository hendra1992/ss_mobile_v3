package com.softwaresekolah.inosoft.presentation.profile.parentData

import com.softwaresekolah.inosoft.data.profile.response.ParentDataResponse

data class ParentDataState (
    var isLoading: Boolean = false,
    var parentData: ParentDataResponse? = null,
    var text: String? = null,
    var dadNameIsError: Boolean = false,
    var dadPhoneIsError:Boolean = false,
    var momNameIsError: Boolean = false,
    var momPhoneIsError: Boolean = false,
    var dadNameErrorText: String? = null,
    var dadPhoneErrorText:String? = null,
    var momNameErrorText: String? = null,
    var momPhoneErrorText: String? = null,
)
