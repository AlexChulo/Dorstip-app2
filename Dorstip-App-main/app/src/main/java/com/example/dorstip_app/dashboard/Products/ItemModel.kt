package com.example.dorstip_app.dashboard.Products

import android.os.Parcel
import android.os.Parcelable

data class ItemModel(
    var title:String="",
    var alcohol:Double=0.0,
    var brewery:String="",
    var description:String="",
    var picUrl:ArrayList<String> = ArrayList(),
    var price:Double=0.0,
    var rating:Double=0.0,
    var type:String=""
):Parcelable{
    constructor(parcel: Parcel):this(
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),

    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeDouble(alcohol)
        dest.writeString(brewery)
        dest.writeString(description)
        dest.writeStringList(picUrl)
        dest.writeDouble(price)
        dest.writeDouble(rating)
        dest.writeString(type)
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }

}
