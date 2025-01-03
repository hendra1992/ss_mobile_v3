package com.softwaresekolah.inosoft.domain.notification.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.notification.requests.DeleteAllReadBodyRequest
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import javax.inject.Inject

class DeleteAllReadNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        body: DeleteAllReadBodyRequest
    ): ApiResponse<BaseResponse<String>> {
        return notificationRepository.deleteAllReadNotification(
            body = body
        )
    }
}