package com.udacity.asteroidradar.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "picture_of_day")
data class PictureOfDay(
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    @ColumnInfo(name = "picture_title")
    @PrimaryKey(autoGenerate = false)
    val title: String,
    @ColumnInfo(name = "picture_url")
    val url: String,

){

}