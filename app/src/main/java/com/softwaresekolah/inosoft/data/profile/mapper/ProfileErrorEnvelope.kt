package com.softwaresekolah.inosoft.data.core.mapper

import com.google.gson.Gson
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import com.softwaresekolah.inosoft.data.profile.mapper.CustomProfileError

data class ProfileErrorEnvelope(
    val code: Int,
    val message: String,
    val body: CustomProfileError
)



// Create a mapper for error responses.
// Within the `map` function, construct an instance of your custom model using the information from `ApiResponse.Failure.Error`.
object ProfileErrorEnvelopeMapper : ApiErrorModelMapper<ProfileErrorEnvelope> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error): ProfileErrorEnvelope {
//        val json = JSONObject(apiErrorResponse.errorBody as Map<*, *>)
        return ProfileErrorEnvelope(apiErrorResponse.statusCode.code, apiErrorResponse.message(), Gson().fromJson(apiErrorResponse.errorBody?.string(), CustomProfileError::class.java))
    }
}

