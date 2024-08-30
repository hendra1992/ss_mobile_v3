package com.softwaresekolah.inosoft.data.core.mapper

import com.google.gson.Gson
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import com.softwaresekolah.inosoft.data.auth.response.LoginDataResponse
import com.softwaresekolah.inosoft.data.core.remote.response.BaseResponse
import org.json.JSONObject

data class ErrorEnvelope(
    val code: Int,
    val message: String,
    val body: BaseResponse<*>
)

// Create a mapper for error responses.
// Within the `map` function, construct an instance of your custom model using the information from `ApiResponse.Failure.Error`.
object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorEnvelope> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error): ErrorEnvelope {
//        val json = JSONObject(apiErrorResponse.errorBody as Map<*, *>)
        return ErrorEnvelope(apiErrorResponse.statusCode.code, apiErrorResponse.message(), Gson().fromJson(apiErrorResponse.errorBody?.string(), BaseResponse::class.java))
    }
}

