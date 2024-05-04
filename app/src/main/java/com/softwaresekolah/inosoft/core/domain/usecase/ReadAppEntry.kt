package com.softwaresekolah.inosoft.core.domain.usecase

import com.softwaresekolah.inosoft.core.domain.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val localManager: LocalManager
){

    operator fun invoke(): Flow<Boolean>{
        return localManager.readAppEntry()
    }
}