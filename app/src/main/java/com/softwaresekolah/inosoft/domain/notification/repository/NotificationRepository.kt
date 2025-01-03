package com.softwaresekolah.inosoft.domain.notification.repository

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.notification.requests.DeleteAllReadBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.data.notification.requests.ReadAllBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.UpdateReadNotificationRequestBody
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.data.notification.responses.CountNotificationResponse
import com.softwaresekolah.inosoft.data.notification.responses.NotificationDetailResponse
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotificationList(studentId: String, departmentId: String): Flow<List<NotificationListResponse>>
    suspend fun getNotificationCount(studentId: String, departmentId: String): Flow<CountNotificationResponse>
    suspend fun getNotificationDetail(notificationId: String): Flow<NotificationDetailResponse>

    suspend fun updateReadBatchNotification(body: UpdateReadNotificationRequestBody): ApiResponse<BaseResponse<String>>
    suspend fun deleteBatchNotification(body: DeleteBatchNotificationRequest): ApiResponse<BaseResponse<String>>
    suspend fun readAllNotification(body: ReadAllBodyRequest): ApiResponse<BaseResponse<String>>
    suspend fun deleteAllReadNotification(body: DeleteAllReadBodyRequest): ApiResponse<BaseResponse<String>>
}