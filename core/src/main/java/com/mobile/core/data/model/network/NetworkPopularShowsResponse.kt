package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkPopularShowsResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<NetworkShowOverview>,
    @SerializedName("total_pages") val totalPages: Int? = null
)