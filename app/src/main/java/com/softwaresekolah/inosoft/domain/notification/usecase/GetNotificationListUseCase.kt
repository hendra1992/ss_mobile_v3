package com.softwaresekolah.inosoft.domain.notification.usecase

import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<List<NotificationListResponse>> {
        return notificationRepository.getNotificationList(studentId = studentId, departmentId = departmentId)
    }
}   