package com.data.hilt_live.di

import com.data.hilt_live.database.PhotoDao
import com.data.hilt_live.network.ApiService
import com.data.hilt_live.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        photoDao: PhotoDao,
        apiService: ApiService
    ): MainRepository {
        return MainRepository(photoDao, apiService)
    }
}