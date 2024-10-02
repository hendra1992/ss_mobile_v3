package com.softwaresekolah.inosoft.domain.profile.usecase.etc

import com.softwaresekolah.inosoft.data.profile.response.ReligionDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReligionsUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {

    suspend operator fun invoke(): Flow<List<ReligionDataResponse>> {
        return formDataProfileRepository.getReligions()
    }
}