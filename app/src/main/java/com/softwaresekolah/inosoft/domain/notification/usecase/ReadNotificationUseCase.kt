package com.softwaresekolah.inosoft.domain.notification.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.notification.requests.UpdateReadNotificationRequestBody
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import javax.inject.Inject

class ReadNotificationUseCase  @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        body: UpdateReadNotificationRequestBody
    ): ApiResponse<BaseResponse<String>> {
        return notificationRepository.updateReadBatchNotification(
            body = body
        )
    }
}