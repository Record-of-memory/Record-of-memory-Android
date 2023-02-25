package com.recordOfMemory.src.main.home.diary2.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GridRecord(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("imgUrl") val imgUrl: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GridRecord> {
        override fun createFromParcel(parcel: Parcel): GridRecord {
            return GridRecord(parcel)
        }

        override fun newArray(size: Int): Array<GridRecord?> {
            return arrayOfNulls(size)
        }
    }
}
