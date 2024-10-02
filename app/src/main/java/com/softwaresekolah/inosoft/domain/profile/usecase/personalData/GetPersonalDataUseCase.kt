package com.softwaresekolah.inosoft.domain.profile.usecase.personalData

import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.data.profile.response.PersonalDataResponse
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonalDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<PersonalDataResponse> {
        return formDataProfileRepository.getUserPersonalData(studentId = studentId, departmentId = departmentId)
    }
}