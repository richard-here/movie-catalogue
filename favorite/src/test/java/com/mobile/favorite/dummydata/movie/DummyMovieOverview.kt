package com.mobile.favorite.dummydata.movie

import com.mobile.core.domain.model.movie.MovieOverview

object DummyMovieOverview {
    val list = listOf(
        MovieOverview(
            1,
            "Title 1",
            1.0f,
            2001,
            "Summary 1",
            "Url 1",
            listOf(0, 1)
        ),
        MovieOverview(
            2,
            "Title 2",
            2.0f,
            2002,
            "Summary 2",
            "Url 2",
            listOf(0, 2)
        )
    )
}