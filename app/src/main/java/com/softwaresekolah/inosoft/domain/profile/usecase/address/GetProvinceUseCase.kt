package com.softwaresekolah.inosoft.domain.profile.usecase.address

import com.softwaresekolah.inosoft.data.profile.response.ProvinceDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProvinceUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(): Flow<List<ProvinceDataResponse>> {
        return formDataProfileRepository.getProvinces()
    }
}