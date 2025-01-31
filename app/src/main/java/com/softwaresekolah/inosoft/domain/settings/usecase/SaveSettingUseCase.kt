package com.softwaresekolah.inosoft.domain.settings.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.settings.requests.SaveSettingRequestBody
import com.softwaresekolah.inosoft.domain.settings.repository.SettingRepository
import javax.inject.Inject

class SaveSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(
        body: SaveSettingRequestBody
    ): ApiResponse<BaseResponse<String>> {
        return settingRepository.saveSetting(
            body = body
        )
    }
}
