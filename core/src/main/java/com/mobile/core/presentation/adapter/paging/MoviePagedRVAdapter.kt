package com.mobile.core.presentation.adapter.paging

import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.presentation.adapter.diff.MovieOverviewDiffCallback
import com.mobile.core.presentation.adapter.listener.ItemClickListener

class MoviePagedRVAdapter(
    layoutId: Int,
    clickListener: ItemClickListener
) : DataBindingPagedRVAdapter<MovieOverview>(layoutId, MovieOverviewDiffCallback(), clickListener)