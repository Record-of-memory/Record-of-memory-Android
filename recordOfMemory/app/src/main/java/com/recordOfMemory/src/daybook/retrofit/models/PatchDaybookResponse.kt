package com.recordOfMemory.src.daybook.retrofit.models

import com.google.gson.annotations.SerializedName

data class PatchDaybookResponse (
	@SerializedName("check") val check:Boolean,
	@SerializedName("information") val information:DaybookDeleteInformation
)
data class DaybookDeleteInformation(
	@SerializedName("message") val message: String
)