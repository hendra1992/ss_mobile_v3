package com.softwaresekolah.inosoft.domain.profile.usecase.parentData

import com.softwaresekolah.inosoft.data.profile.response.ParentDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetParentDataUseCase @Inject constructor(
    private val formDataProfileRepository: FormDataProfileRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<ParentDataResponse> {
        return formDataProfileRepository.getUserParentDataData(studentId = studentId, departmentId = departmentId)
    }
}