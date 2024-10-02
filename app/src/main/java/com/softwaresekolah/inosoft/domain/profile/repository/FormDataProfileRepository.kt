package com.softwaresekolah.inosoft.domain.profile.repository

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
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
import kotlinx.coroutines.flow.Flow

interface FormDataProfileRepository {
    suspend fun getProvinces(): Flow<List<ProvinceDataResponse>>
    suspend fun getCities(provinceId: String): Flow<List<CityDataResponse>>
    suspend fun getReligions(): Flow<List<ReligionDataResponse>>
    suspend fun getUserEtcData(studentId: String, departmentId: String): Flow<EtcDataResponse>
    suspend fun getUserPersonalData(studentId: String, departmentId: String): Flow<PersonalDataResponse>
    suspend fun getUserParentDataData(studentId: String, departmentId: String): Flow<ParentDataResponse>
    suspend fun getUserAddressData(studentId: String, departmentId: String): Flow<AddressDataResponse>

    suspend fun saveUserEtcData(body: EtcDataBodyRequest): ApiResponse<BaseResponse<EtcDataResponse>>
    suspend fun saveUserPersonalData(body: PersonalBodyRequest): ApiResponse<BaseResponse<PersonalDataResponse>>
    suspend fun saveUserParentData(body: ParentDataBodyRequest): ApiResponse<BaseResponse<ParentDataResponse>>
    suspend fun saveUserAddressData(body: AddressBodyRequest): ApiResponse<BaseResponse<AddressDataResponse>>

}