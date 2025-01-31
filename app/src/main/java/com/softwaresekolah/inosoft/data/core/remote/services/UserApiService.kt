package com.softwaresekolah.inosoft.data.core.remote.services

import com.skydoves.sandwich.ApiResponse
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import com.softwaresekolah.inosoft.data.core.remote.response.ConfigDataResponse
import com.softwaresekolah.inosoft.data.notification.requests.DeleteAllReadBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.data.notification.requests.ReadAllBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.UpdateReadNotificationRequestBody
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.data.notification.responses.CountNotificationResponse
import com.softwaresekolah.inosoft.data.notification.responses.NotificationDetailResponse
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
import com.softwaresekolah.inosoft.data.settings.requests.ChangePasswordRequestBody
import com.softwaresekolah.inosoft.data.settings.requests.SaveSettingRequestBody
import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
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

    @GET("pemberitahuan/count_unread")
    suspend fun getCountNotif(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<CountNotificationResponse>>

    @GET("pemberitahuan")
    suspend fun getNotificationList(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<MutableList<NotificationListResponse>>>

    @GET("pemberitahuan/detail")
    suspend fun getNotificationDetail(
        @Query("id_notif") notificationId : String
    ): ApiResponse<BaseResponse<NotificationDetailResponse>>


    @POST("pemberitahuan/update_batch_read")
    suspend fun updateBatchReadNotification(
        @Body body: UpdateReadNotificationRequestBody
    ): ApiResponse<BaseResponse<String>>


    @POST("pemberitahuan/delete_batch")
    suspend fun deleteBatchNotification(
        @Body body: DeleteBatchNotificationRequest
    ): ApiResponse<BaseResponse<String>>

    @POST("pemberitahuan/update_all_read")
    suspend fun readAllNotification(
        @Body body: ReadAllBodyRequest
    ): ApiResponse<BaseResponse<String>>


    @POST("pemberitahuan/delete_all_read")
    suspend fun deleteAllReadNotification(
        @Body body: DeleteAllReadBodyRequest
    ): ApiResponse<BaseResponse<String>>

    @GET("pengaturan")
    suspend fun getSettings(
        @Query("id_siswa") studentId : String,
        @Query("id_dep") departmentId : String
    ): ApiResponse<BaseResponse<GetSettingResponse>>

    @POST("pengaturan")
    suspend fun saveSettings(
        @Body body: SaveSettingRequestBody
    ): ApiResponse<BaseResponse<String>>

    @POST("ganti_password")
    suspend fun changePassword(
        @Body body: ChangePasswordRequestBody
    ): ApiResponse<BaseResponse<String>>
}