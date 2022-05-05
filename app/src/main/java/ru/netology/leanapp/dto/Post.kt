package ru.netology.leanapp.dto

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val id: Long,
    val author: String?,
    val text: String?,
    val likes: Int,
    val likedByMe: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(author)
        parcel.writeString(text)
        parcel.writeInt(likes)
        parcel.writeByte(if (likedByMe) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
