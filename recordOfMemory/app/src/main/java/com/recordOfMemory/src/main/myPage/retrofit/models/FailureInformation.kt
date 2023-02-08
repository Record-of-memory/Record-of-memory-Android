package com.recordOfMemory.src.main.myPage.retrofit.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

open class FailureInformation(
	@SerializedName("timestamp") val timestamp:String="",
	@SerializedName("message") open val message: String?="",
	@SerializedName("code") val code:String="",
	@SerializedName("status") val status:Int=0,
	@SerializedName("class") val classs:String="",
	@SerializedName("errors") val errors: JSONObject= JSONObject()
)