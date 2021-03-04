package com.mobile.core.data.source.remote.service

import com.mobile.core.data.model.network.NetworkPopularShowsResponse
import com.mobile.core.data.model.network.NetworkShowDetailsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@ExperimentalCoroutinesApi
interface ShowService {
    @GET("tv/popular")
    suspend fun getPopularShows(
        @Query("api_key") apiKey: String = RetrofitService.API_KEY,
        @Query("page") page: Int
    ): NetworkPopularShowsResponse

    @GET("tv/{id}")
    suspend fun getShowById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = RetrofitService.API_KEY
    ): NetworkShowDetailsResponse
}