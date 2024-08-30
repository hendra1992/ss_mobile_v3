package com.softwaresekolah.inosoft.data.core.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.onSuccess
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class ConfigAppRepositoryImpl @Inject constructor(
    private val userApi: UserApiService,
) : ConfigAppRepository {
    override suspend fun readConfig(body: ConfigRequestBody): Flow<ConfigDataResponse> {
        val response = userApi.getConfig(body)
        return flow { response }
    }
}