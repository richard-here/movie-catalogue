package com.mobile.core.domain.model.movie

import com.mobile.core.domain.model.Genre

data class Movie(
    val backdropUrl: String = "",
    val genres: List<Genre> = listOf(),
    val id: Int = 0,
    val title: String = "",
    val summary: String = "",
    val coverUrl: String = "",
    val year: Int = 0,
    val runtime: Int = 0,
    val rating: Float = 0f,
    val voteCount: Int = 0
)

fun Movie.isEmptyMovie(): Boolean {
    return this.title == "" && this.id == 0
}