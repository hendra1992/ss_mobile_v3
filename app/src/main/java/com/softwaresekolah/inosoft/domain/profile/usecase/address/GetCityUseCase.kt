package com.softwaresekolah.inosoft.domain.profile.usecase.address

import com.softwaresekolah.inosoft.data.profile.response.CityDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(provinceId: String): Flow<List<CityDataResponse>> {
        return formDataProfileRepository.getCities(provinceId)
    }
}