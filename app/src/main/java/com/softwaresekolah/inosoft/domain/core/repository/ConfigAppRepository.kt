package com.softwaresekolah.inosoft.domain.core.repository

import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
import kotlinx.coroutines.flow.Flow

interface ConfigAppRepository {
    suspend fun readConfig(body: ConfigRequestBody): Flow<ConfigDataResponse>
}