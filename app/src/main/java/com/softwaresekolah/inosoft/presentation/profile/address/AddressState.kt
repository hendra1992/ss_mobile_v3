package com.softwaresekolah.inosoft.presentation.profile.address

import com.softwaresekolah.inosoft.data.profile.response.AddressDataResponse
import com.softwaresekolah.inosoft.data.profile.response.CityDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ProvinceDataResponse

data class AddressState (
    var isLoading: Boolean = false,
    var addressData: AddressDataResponse? = null,
    var text: String? = null,
    var success: String? = null,
    var provinceRaw: List<ProvinceDataResponse> = emptyList(),
    var provinces: List<String> = emptyList(),
    var cityRaw: List<CityDataResponse> = emptyList(),
    var cities: List<String> = emptyList(),
    var addressIsError: Boolean = false,
    var provinceIdIsError: Boolean = false,
    var cityIdIsError: Boolean = false,
    var postalCodeIsError: Boolean = false,
    var telephoneIsError: Boolean = false,
    var addressErrorText: String? = null,
    var provinceIdErrorText: String? = null,
    var cityIdErrorText: String? = null,
    var postalCodeErrorText: String? = null,
    var telephoneErrorText: String? = null,
)