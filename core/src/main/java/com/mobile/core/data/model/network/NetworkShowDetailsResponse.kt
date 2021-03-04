package com.mobile.core.data.model.network

import com.google.gson.annotations.SerializedName

data class NetworkShowDetailsResponse(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("episode_run_time") val runtime: List<Int>?,
    @SerializedName("first_air_date") val airDate: String?,
    @SerializedName("genres") val genres: List<NetworkGenre>?,
    @SerializedName("id") val id: Int,
    @SerializedName("status") val returningSeries: String?,
    @SerializedName("name") val title: String?,
    @SerializedName("number_of_episodes") val episodes: Int?,
    @SerializedName("number_of_seasons") val seasons: Int?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)

