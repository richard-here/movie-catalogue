package com.mobile.core.presentation.adapter.listener

import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.model.show.ShowOverview

interface BindableListener {
    fun onClickMovie(movieOverview: MovieOverview)

    fun onClickShow(showOverview: ShowOverview)
}