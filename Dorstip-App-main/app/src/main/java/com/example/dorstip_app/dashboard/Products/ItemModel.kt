package com.example.dorstip_app.dashboard.Products

import android.os.Parcel
import android.os.Parcelable

// Define a data class representing an item
data class ItemModel(
    var id: Int = 0,
    var title: String = "",
    var alcohol: Double = 0.0,
    var brewery: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var type: String = ""
) : Parcelable {

    // Constructor for parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString()
    )

    // Return zero as there is no special content
    override fun describeContents(): Int {
        return 0
    }

    // Write data to parcel
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeDouble(alcohol)
        dest.writeString(brewery)
        dest.writeString(description)
        dest.writeStringList(picUrl)
        dest.writeDouble(price)
        dest.writeDouble(rating)
        dest.writeString(type)
    }

    // Companion object implementing Parcelable.Creator interface
    companion object CREATOR : Parcelable.Creator<ItemModel> {
        // Create ItemModel from parcel
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        // Create array of ItemModel
        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
