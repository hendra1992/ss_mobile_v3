package com.softwaresekolah.inosoft.domain.profile.usecase.etc

import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEtcDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<EtcDataResponse> {
        return formDataProfileRepository.getUserEtcData(studentId = studentId, departmentId = departmentId)
    }
}