package com.softwaresekolah.inosoft.domain.profile.usecase.etc

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.profile.request.EtcDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import javax.inject.Inject

class SaveEtcDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
){
    suspend operator fun invoke(body: EtcDataBodyRequest): ApiResponse<BaseResponse<EtcDataResponse>>{
        return formDataProfileRepository.saveUserEtcData(body = body)
    }
}