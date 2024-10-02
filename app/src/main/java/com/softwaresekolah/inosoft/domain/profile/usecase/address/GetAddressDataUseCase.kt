package com.softwaresekolah.inosoft.domain.profile.usecase.address

import com.softwaresekolah.inosoft.data.profile.response.AddressDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<AddressDataResponse> {
        return formDataProfileRepository.getUserAddressData(studentId = studentId, departmentId = departmentId)
    }
}