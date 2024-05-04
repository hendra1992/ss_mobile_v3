package com.softwaresekolah.inosoft.core.domain.usecase

import com.softwaresekolah.inosoft.core.domain.manager.LocalManager
import javax.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localManager: LocalManager
){

    suspend operator fun invoke(){
        localManager.saveAppEntry()
    }
}