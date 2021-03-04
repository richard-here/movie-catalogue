package com.mobile.core.domain.model.movie

data class MovieOverview(
    val id: Int,
    val title: String,
    val rating: Float,
    val year: Int,
    val summary: String,
    val coverUrl: String,
    val genreIds: List<Int>
)