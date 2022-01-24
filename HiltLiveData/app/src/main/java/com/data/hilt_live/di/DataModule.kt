package com.data.hilt_live.di

import android.content.Context
import androidx.room.Room
import com.data.hilt_live.database.PhotoDao
import com.data.hilt_live.database.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context, PhotoDatabase::class.java,
            PhotoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDAO(photoDatabase: PhotoDatabase): PhotoDao {
        return photoDatabase.photoDao()
    }
}