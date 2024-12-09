package com.softwaresekolah.inosoft.presentation.profile.etc

import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ReligionDataResponse

data class EtcState(
    var isLoading: Boolean = false,
    var religionRaw: List<ReligionDataResponse> = emptyList(),
    var religions: List<String> = emptyList(),
    var etcData: EtcDataResponse? = null,
    var text: String? = null,
    var success: String? = null
)