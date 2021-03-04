package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkGenre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?
)