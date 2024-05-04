package com.softwaresekolah.inosoft.auth.domain.usecase

import com.softwaresekolah.inosoft.core.domain.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserLogin @Inject constructor(
    private val localManager: LocalManager
){

    operator fun invoke(): Flow<Boolean> {
        return localManager.readUserLogin()
    }
}