package com.recordOfMemory.src.main.home.diary2.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetRecordResponse(
    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("date") val date : String,
    @SerializedName("user") val user : String,
    @SerializedName("status") val status: String,
    @SerializedName("diary") val diary : String,
    @SerializedName("likeCnt") val likeCnt : String,
    @SerializedName("cmtCnt") val cmtCnt : String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(imgUrl)
        parcel.writeString(date)
        parcel.writeString(user)
        parcel.writeString(status)
        parcel.writeString(diary)
        parcel.writeString(likeCnt)
        parcel.writeString(cmtCnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetRecordResponse> {
        override fun createFromParcel(parcel: Parcel): GetRecordResponse {
            return GetRecordResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetRecordResponse?> {
            return arrayOfNulls(size)
        }
    }
}
