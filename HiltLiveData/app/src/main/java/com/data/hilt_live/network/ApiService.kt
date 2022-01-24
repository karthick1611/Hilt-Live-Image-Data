package com.data.hilt_live.network

import com.data.hilt_live.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_IMAGES="search"

interface ApiService {

    @GET(GET_IMAGES)
    suspend fun get(@Query("query")  query: String, @Query("per_page")  count: Int): ImageResponse
}