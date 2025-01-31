package com.softwaresekolah.inosoft.domain.settings.usecase

import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
import com.softwaresekolah.inosoft.domain.settings.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<GetSettingResponse> {
        return settingRepository.getSetting(studentId = studentId, departmentId = departmentId)
    }
}