package com.softwaresekolah.inosoft.domain.core.usecase

import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadFcmToken @Inject constructor(
    private val localManager: LocalManager
){

    operator fun invoke(): Flow<String> {
        return localManager.readUserFCMToken()
    }
}