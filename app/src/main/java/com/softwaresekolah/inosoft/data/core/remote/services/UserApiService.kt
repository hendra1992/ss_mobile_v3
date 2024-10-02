package com.softwaresekolah.inosoft.data.core.remote.services

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    @POST("config")
    suspend fun getConfig(
        @Body body: ConfigRequestBody
    ): ApiResponse<BaseResponse<ConfigDataResponse>>

    @GET("master/province")
    suspend fun getProvinces(): ApiResponse<BaseResponse<List<ProvinceDataResponse>>>

    @GET("master/city")
    suspend fun getCities(
        @Query("id_prop") idProvince : String
    ): ApiResponse<BaseResponse<List<CityDataResponse>>>

    @GET("master/religion")
    suspend fun getReligions(): ApiResponse<BaseResponse<List<ReligionDataResponse>>>

    @GET("siswa/data_diri")
    suspend fun getPersonalData(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<PersonalDataResponse>>

    @GET("siswa/data_alamat")
    suspend fun getAddress(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<AddressDataResponse>>

    @GET("siswa/data_ortu")
    suspend fun getParentData(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<ParentDataResponse>>

    @GET("siswa/data_lainnya")
    suspend fun getEtcData(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<EtcDataResponse>>

    @POST("siswa/data_diri")
    suspend fun savePersonalData(
        @Body body: PersonalBodyRequest
    ): ApiResponse<BaseResponse<PersonalDataResponse>>

    @POST("siswa/data_alamat")
    suspend fun saveAddressData(
        @Body body: AddressBodyRequest
    ): ApiResponse<BaseResponse<AddressDataResponse>>

    @POST("siswa/data_ortu")
    suspend fun saveParentData(
        @Body body: ParentDataBodyRequest
    ): ApiResponse<BaseResponse<ParentDataResponse>>

    @POST("siswa/data_lainnya")
    suspend fun saveEtcData(
        @Body body: EtcDataBodyRequest
    ): ApiResponse<BaseResponse<EtcDataResponse>>
}