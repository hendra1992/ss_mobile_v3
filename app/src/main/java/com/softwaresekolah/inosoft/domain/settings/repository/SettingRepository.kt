package com.softwaresekolah.inosoft.domain.settings.repository

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.settings.requests.ChangePasswordRequestBody
import com.softwaresekolah.inosoft.data.settings.requests.SaveSettingRequestBody
import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun getSetting(studentId: String, departmentId: String): Flow<GetSettingResponse>

    suspend fun saveSetting(body: SaveSettingRequestBody): ApiResponse<BaseResponse<String>>
    suspend fun changePassword(body: ChangePasswordRequestBody): ApiResponse<BaseResponse<String>>
}
