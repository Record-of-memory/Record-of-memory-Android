package kr.co.app.recordOfMemory.src.main.home.diary2.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kr.co.app.recordOfMemory.src.main.home.diary2.member.models.GetUserResponse
import java.io.Serializable

data class GetMemberRecordResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("date") val date: String,
    @SerializedName("user") val user: GetUserResponse,
    @SerializedName("likeCount") val likeCount: String,
    @SerializedName("commentCount") val commentCount: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable(GetUserResponse::class.java.classLoader)!!,
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(imgUrl)
        parcel.writeString(date)
        parcel.writeParcelable(user, flags)
        parcel.writeString(likeCount)
        parcel.writeString(commentCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetMemberRecordResponse> {
        override fun createFromParcel(parcel: Parcel): GetMemberRecordResponse {
            return GetMemberRecordResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetMemberRecordResponse?> {
            return arrayOfNulls(size)
        }
    }
}

