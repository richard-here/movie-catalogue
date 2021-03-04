package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkMovieOverview (
    @SerializedName("popularity") val popularity: Float?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?
)
