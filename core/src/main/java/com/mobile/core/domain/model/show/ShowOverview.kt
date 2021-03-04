package com.mobile.core.domain.model.show

data class ShowOverview(
    val id: Int,
    val title: String,
    val rating: Float,
    val firstAired: String,
    val summary: String,
    val coverUrl: String,
    val genreIds: List<Int>
)