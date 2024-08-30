package com.softwaresekolah.inosoft.domain.auth.usecase

import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import javax.inject.Inject

class RemoveUserLogin @Inject constructor(
    private val localManager: LocalManager
){

    suspend operator fun invoke(){
        localManager.removeUserLogin()
    }
}