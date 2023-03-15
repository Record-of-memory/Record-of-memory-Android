package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class PatchDaybookRequest(
	@SerializedName("recordId") val recordId:String
)
