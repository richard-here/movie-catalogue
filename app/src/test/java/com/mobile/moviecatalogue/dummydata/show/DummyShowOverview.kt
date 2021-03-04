package com.mobile.moviecatalogue.dummydata.show

import com.mobile.core.domain.model.show.ShowOverview

object DummyShowOverview {
    val list = listOf(
        ShowOverview(
            1,
            "Title 1",
            1.0f,
            "2001",
            "Summary 1",
            "Url 1",
            listOf(0, 1)
        ),
        ShowOverview(
            2,
            "Title 2",
            2.0f,
            "2003",
            "Summary 2",
            "Url 2",
            listOf(0, 2)
        )
    )
}