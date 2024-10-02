package com.softwaresekolah.inosoft.domain.profile.usecase.address

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.profile.request.AddressBodyRequest
import com.softwaresekolah.inosoft.data.profile.response.AddressDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import javax.inject.Inject

class SaveAddressDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
){
    suspend operator fun invoke(body: AddressBodyRequest): ApiResponse<BaseResponse<AddressDataResponse>> {
        return formDataProfileRepository.saveUserAddressData(body = body)
    }
}