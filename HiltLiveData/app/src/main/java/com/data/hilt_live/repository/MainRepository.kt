package com.data.hilt_live.repository

import com.data.hilt_live.database.PhotoDao
import com.data.hilt_live.model.ImageResponse
import com.data.hilt_live.network.ApiService
import com.data.hilt_live.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val photoDao: PhotoDao,
    private val apiService: ApiService
) {
    suspend fun getPhoto(query: String): Flow<DataState<ImageResponse>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkPhotos = apiService.get(query,50)
            emit(DataState.Success(networkPhotos))
//            photoDao.emptyTable()
            photoDao.insert(networkPhotos)
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}