package kr.co.app.recordOfMemory.src.daybook.retrofit.models

import java.io.Serializable

data class DaybookToWriting(
	val recordId:String,
	val diaryTitle:String,
	val date:String,
	val title:String,
	val content:String,
	val imgUrl:String
): Serializable
