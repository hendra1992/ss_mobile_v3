package com.softwaresekolah.inosoft.domain.notification.usecase

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.notification.requests.ReadAllBodyRequest
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import javax.inject.Inject

class ReadAllNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        body: ReadAllBodyRequest
    ): ApiResponse<BaseResponse<String>> {
        return notificationRepository.readAllNotification(
            body = body
        )
    }
}