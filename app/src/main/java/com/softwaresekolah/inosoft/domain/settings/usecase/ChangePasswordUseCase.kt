package com.softwaresekolah.inosoft.domain.settings.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.settings.requests.ChangePasswordRequestBody
import com.softwaresekolah.inosoft.domain.settings.repository.SettingRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(
        body: ChangePasswordRequestBody
    ): ApiResponse<BaseResponse<String>> {
        return settingRepository.changePassword(
            body = body
        )
    }
}