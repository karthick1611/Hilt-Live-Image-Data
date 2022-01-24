package com.data.hilt_live.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.data.hilt_live.model.ImageResponse

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photoEntity: ImageResponse)

    @Query("DELETE FROM Photos")
    suspend fun emptyTable()
}