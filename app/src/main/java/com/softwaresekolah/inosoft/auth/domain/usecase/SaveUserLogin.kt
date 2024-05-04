package com.softwaresekolah.inosoft.auth.domain.usecase

import com.softwaresekolah.inosoft.core.domain.manager.LocalManager
import javax.inject.Inject

class SaveUserLogin @Inject constructor(
    private val localManager: LocalManager
){

    suspend operator fun invoke(){
        localManager.saveUserLogin()
    }
}