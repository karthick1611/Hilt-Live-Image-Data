package com.data.hilt_live.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.data.hilt_live.model.ImageResponse
import com.data.hilt_live.model.ImageSrc
import com.data.hilt_live.model.ListPhotos

@Database(entities = [ImageResponse::class], version = 4, exportSchema = false)
@TypeConverters(ListPhotos::class, ImageSrc::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        const val DATABASE_NAME: String = "photo_db"
    }
}