package com.softwaresekolah.inosoft.data.notification.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.notification.requests.DeleteAllReadBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.data.notification.requests.ReadAllBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.UpdateReadNotificationRequestBody
import com.softwaresekolah.inosoft.data.notification.responses.CountNotificationResponse
import com.softwaresekolah.inosoft.data.notification.responses.NotificationDetailResponse
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : NotificationRepository{

    override suspend fun getNotificationList(
        studentId: String,
        departmentId: String
    ): Flow<List<NotificationListResponse>> {
         val response = userApiService.getNotificationList(
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

    override suspend fun getNotificationCount(
        studentId: String,
        departmentId: String
    ): Flow<CountNotificationResponse> {
        val response = userApiService.getCountNotif(
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

    override suspend fun getNotificationDetail(notificationId: String): Flow<NotificationDetailResponse> {
        val response = userApiService.getNotificationDetail(
             notificationId = notificationId
         )
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun updateReadBatchNotification(body: UpdateReadNotificationRequestBody): ApiResponse<BaseResponse<String>> {
         return userApiService.updateBatchReadNotification(body = body)
    }

    override suspend fun deleteBatchNotification(body: DeleteBatchNotificationRequest): ApiResponse<BaseResponse<String>> {
        return userApiService.deleteBatchNotification(body = body)
    }

    override suspend fun readAllNotification(body: ReadAllBodyRequest): ApiResponse<BaseResponse<String>> {
         return userApiService.readAllNotification(body = body)
    }
    override suspend fun deleteAllReadNotification(body: DeleteAllReadBodyRequest): ApiResponse<BaseResponse<String>> {
         return userApiService.deleteAllReadNotification(body = body)
    }

}