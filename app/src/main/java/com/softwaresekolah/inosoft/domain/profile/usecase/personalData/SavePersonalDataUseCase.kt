package com.softwaresekolah.inosoft.domain.profile.usecase.personalData

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.profile.request.EtcDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.request.PersonalBodyRequest
import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.data.profile.response.PersonalDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import javax.inject.Inject

class SavePersonalDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
){
    suspend operator fun invoke(body: PersonalBodyRequest): ApiResponse<BaseResponse<PersonalDataResponse>>{
        return formDataProfileRepository.saveUserPersonalData(body = body)
    }
}