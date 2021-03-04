package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkMovieDetailsResponse(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<NetworkGenre>?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)

