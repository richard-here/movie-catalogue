package com.mobile.core.presentation.adapter.listener

import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.model.show.ShowOverview

class ItemClickListener(val clickListener: (id: Int) -> Unit) :
    BindableListener {
    override fun onClickMovie(movieOverview: MovieOverview) = clickListener(movieOverview.id)

    override fun onClickShow(showOverview: ShowOverview) = clickListener(showOverview.id)
}