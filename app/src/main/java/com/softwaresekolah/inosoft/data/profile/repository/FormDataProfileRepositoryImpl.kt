package com.softwaresekolah.inosoft.data.profile.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.services.UserApiService
import com.softwaresekolah.inosoft.data.profile.request.AddressBodyRequest
import com.softwaresekolah.inosoft.data.profile.request.EtcDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.request.ParentDataBodyRequest
import com.softwaresekolah.inosoft.data.profile.request.PersonalBodyRequest
import com.softwaresekolah.inosoft.data.profile.response.AddressDataResponse
import com.softwaresekolah.inosoft.data.profile.response.CityDataResponse
import com.softwaresekolah.inosoft.data.profile.response.EtcDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ParentDataResponse
import com.softwaresekolah.inosoft.data.profile.response.PersonalDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ProvinceDataResponse
import com.softwaresekolah.inosoft.data.profile.response.ReligionDataResponse
import com.softwaresekolah.inosoft.domain.profile.repository.FormDataProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FormDataProfileRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : FormDataProfileRepository {
    override suspend fun getProvinces(): Flow<List<ProvinceDataResponse>> {
        val response = userApiService.getProvinces()
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }
    override suspend fun getCities(provinceId: String): Flow<List<CityDataResponse>> {
        val response = userApiService.getCities(provinceId)
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }
    override suspend fun getReligions(): Flow<List<ReligionDataResponse>> {
        val response = userApiService.getReligions()
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun getUserEtcData(studentId: String, departmentId: String): Flow<EtcDataResponse> {
         val response = userApiService.getEtcData(studentId = studentId, departmentId = departmentId)
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun getUserPersonalData(
        studentId: String,
        departmentId: String
    ): Flow<PersonalDataResponse> {
        val response = userApiService.getPersonalData(studentId = studentId, departmentId = departmentId)
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun getUserParentDataData(
        studentId: String,
        departmentId: String
    ): Flow<ParentDataResponse> {
        val response = userApiService.getParentData(studentId = studentId, departmentId = departmentId)
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun getUserAddressData(
        studentId: String,
        departmentId: String
    ): Flow<AddressDataResponse> {
        val response = userApiService.getAddress(studentId = studentId, departmentId = departmentId)
        return flow {
            response.suspendOnSuccess {
                data.data?.let {
                     emit(it)
                }
            }
        }
    }

    override suspend fun saveUserEtcData(body: EtcDataBodyRequest): ApiResponse<BaseResponse<EtcDataResponse>> {
         return userApiService.saveEtcData(body = body)
    }

    override suspend fun saveUserPersonalData(body: PersonalBodyRequest): ApiResponse<BaseResponse<PersonalDataResponse>> {
        return userApiService.savePersonalData(body = body)
    }

    override suspend fun saveUserParentData(body: ParentDataBodyRequest): ApiResponse<BaseResponse<ParentDataResponse>> {
         return userApiService.saveParentData(body = body)
    }

    override suspend fun saveUserAddressData(body: AddressBodyRequest): ApiResponse<BaseResponse<AddressDataResponse>> {
         return userApiService.saveAddressData(body = body)
    }
}