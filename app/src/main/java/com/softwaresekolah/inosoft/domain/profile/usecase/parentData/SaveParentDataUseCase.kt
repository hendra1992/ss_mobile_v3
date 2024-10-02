package com.softwaresekolah.inosoft.domain.profile.usecase.parentData

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.profile.request.EtcDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.request.ParentDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ParentDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import javax.inject.Inject

class SaveParentDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
){
    suspend operator fun invoke(body: ParentDataBodyRequest): ApiResponse<BaseResponse<ParentDataResponse>>{
        return formDataProfileRepository.saveUserParentData(body = body)
    }
}