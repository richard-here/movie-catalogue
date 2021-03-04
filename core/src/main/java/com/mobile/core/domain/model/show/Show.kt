package com.mobile.core.domain.model.show

import com.mobile.core.domain.model.Genre

data class Show(
    val backdropUrl: String = "",
    val runtime: Int = 0,
    val firstAired: String = "",
    val genres: List<Genre> = listOf(),
    val id: Int = 0,
    val returningSeries: Boolean = false,
    val title: String = "",
    val episodes: Int = 0,
    val seasons: Int = 0,
    val summary: String = "",
    val coverUrl: String = "",
    val rating: Float = 0f,
    val voteCount: Int = 0
)

fun Show.isEmptyShow() = this.title == "" && this.id == 0