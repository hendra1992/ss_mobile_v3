package com.softwaresekolah.inosoft.domain.notification.usecase

import com.softwaresekolah.inosoft.data.notification.responses.CountNotificationResponse
import com.softwaresekolah.inosoft.domain.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationCountUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(studentId: String, departmentId: String): Flow<CountNotificationResponse> {
        return notificationRepository.getNotificationCount(studentId = studentId, departmentId = departmentId)
    }
}