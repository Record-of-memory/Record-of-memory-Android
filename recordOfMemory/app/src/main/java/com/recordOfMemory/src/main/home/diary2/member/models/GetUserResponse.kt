package com.recordOfMemory.src.main.home.diary2.member.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetUserResponse (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("role") val role: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(nickname)
        parcel.writeString(imageUrl)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetUserResponse> {
        override fun createFromParcel(parcel: Parcel): GetUserResponse {
            return GetUserResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetUserResponse?> {
            return arrayOfNulls(size)
        }
    }
}
