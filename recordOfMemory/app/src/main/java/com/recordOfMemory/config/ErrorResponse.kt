package com.recordOfMemory.config

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("check") val check : Boolean,
    @SerializedName("information") val information: ErrorContent
)
data class ErrorContent(
    @SerializedName("timestamp") val timestamp : String,
    @SerializedName("message") val message : String,
    @SerializedName("code") val code: Nothing? = null,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: Array<Any>? = null
)
