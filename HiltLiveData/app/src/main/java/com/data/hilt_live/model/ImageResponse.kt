package com.data.hilt_live.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

//@Entity(tableName = "Photos", indices = [Index(value = ["page"], unique = true)])
@Entity(tableName = "Photos")
data class ImageResponse(
    var next_page: String?,
    @PrimaryKey var page: Int?,
    var per_page: Int?,
    @TypeConverters(ListPhotos::class)
    var photos: List<Photo>?,
    var total_results: Int?
)

data class Photo(
    var alt: String?,
    var avg_color: String?,
    var height: Int?,
    var id: Int?,
    var liked: Boolean?,
    var photographer: String?,
    var photographer_id: Int?,
    var photographer_url: String?,
    @TypeConverters(ImageSrc::class)
    var src: Src?,
    var url: String?,
    var width: Int?
) : Serializable

data class Src(
    var landscape: String?,
    var large: String?,
    var large2x: String?,
    var medium: String?,
    var original: String?,
    var portrait: String?,
    var small: String?,
    var tiny: String?
) : Serializable

class ListPhotos {
    @TypeConverter
    fun fromImagesList(value: List<Photo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Photo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toImagesList(value: String): List<Photo> {
        val gson = Gson()
        val type = object : TypeToken<List<Photo>>() {}.type
        return gson.fromJson(value, type)
    }
}

class ImageSrc {
    @TypeConverter
    fun fromSrcList(value: Src): String {
        val gson = Gson()
        val type = object : TypeToken<Src>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSrcList(value: String): Src {
        val gson = Gson()
        val type = object : TypeToken<Src>() {}.type
        return gson.fromJson(value, type)
    }
}
