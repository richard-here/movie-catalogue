package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkPopularMoviesResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<NetworkMovieOverview>,
    @SerializedName("total_pages") val totalPages: Int? = null
)