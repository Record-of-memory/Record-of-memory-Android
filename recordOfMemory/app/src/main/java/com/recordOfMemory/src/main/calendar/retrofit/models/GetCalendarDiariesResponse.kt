package com.recordOfMemory.src.main.calendar.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetCalendarDiariesResponse(
    @SerializedName("check") val check: Boolean,
    @SerializedName("information") val information: ArrayList<Information>
)

data class Information (
    @SerializedName("diaryId") val diaryId: String,
    @SerializedName("diaryName") val diaryName: String,
    @SerializedName("records") val records: ArrayList<Record>
)

data class Record (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("imgUrl") val imgUrl: String? = null,
    @SerializedName("date") val date: String,
    @SerializedName("user") val user: User,
    @SerializedName("likeCount") val likeCount: String,
    @SerializedName("commentCount") val commentCount: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readParcelable(User::class.java.classLoader)!!,
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

    companion object CREATOR : Parcelable.Creator<Record> {
        override fun createFromParcel(parcel: Parcel): Record {
            return Record(parcel)
        }

        override fun newArray(size: Int): Array<Record?> {
            return arrayOfNulls(size)
        }
    }
}

data class User (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("role") val role: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(nickname)
        parcel.writeString(imageUrl)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}