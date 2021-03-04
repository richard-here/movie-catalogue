package com.mobile.core.data.source.remote.service

import com.mobile.core.data.model.network.NetworkMovieDetailsResponse
import com.mobile.core.data.model.network.NetworkPopularMoviesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@ExperimentalCoroutinesApi
interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = RetrofitService.API_KEY,
        @Query("page") page: Int
    ): NetworkPopularMoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = RetrofitService.API_KEY
    ): NetworkMovieDetailsResponse
}