package com.mobile.core.data.source.remote.service

import com.mobile.core.BuildConfig
import retrofit2.Retrofit

object RetrofitService {
    var retrofit: Retrofit? = null

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = BuildConfig.API_KEY
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
}