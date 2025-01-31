package com.softwaresekolah.inosoft.data.settings.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.settings.requests.ChangePasswordRequestBody
import com.softwaresekolah.inosoft.data.settings.requests.SaveSettingRequestBody
import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import com.softwaresekolah.inosoft.domain.settings.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : SettingRepository {
    override suspend fun getSetting(
        studentId: String,
        departmentId: String
    ): Flow<GetSettingResponse> {
        val response = userApiService.getSettings(
             studentId = studentId,
             departmentId = departmentId
         )
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun saveSetting(body: SaveSettingRequestBody): ApiResponse<BaseResponse<String>> {
        return userApiService.saveSettings(body = body)
    }

    override suspend fun changePassword(body: ChangePasswordRequestBody): ApiResponse<BaseResponse<String>> {
        return userApiService.changePassword(body = body)
    }

}