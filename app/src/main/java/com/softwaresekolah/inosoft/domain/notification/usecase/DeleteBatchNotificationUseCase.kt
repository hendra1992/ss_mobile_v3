package com.softwaresekolah.inosoft.domain.notification.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import javax.inject.Inject

class DeleteBatchNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        body: DeleteBatchNotificationRequest
    ): ApiResponse<BaseResponse<String>> {
        return notificationRepository.deleteBatchNotification(
            body = body
        )
    }
}