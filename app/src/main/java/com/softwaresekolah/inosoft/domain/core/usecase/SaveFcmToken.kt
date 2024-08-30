package com.softwaresekolah.inosoft.domain.core.usecase

import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import javax.inject.Inject

class SaveFcmToken @Inject constructor(
    private val localManager: LocalManager
){

    suspend operator fun invoke(token: String){
        localManager.saveUserFCMToken(token)
    }
}