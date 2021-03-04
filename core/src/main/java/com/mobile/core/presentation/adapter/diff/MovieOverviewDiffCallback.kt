package com.mobile.core.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.mobile.core.domain.model.movie.MovieOverview

class MovieOverviewDiffCallback : DiffUtil.ItemCallback<MovieOverview>() {
    override fun areItemsTheSame(
        oldItem: MovieOverview,
        newItem: MovieOverview
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieOverview,
        newItem: MovieOverview
    ): Boolean {
        return oldItem.coverUrl == newItem.coverUrl
                && oldItem.rating == newItem.rating
                && oldItem.summary == newItem.summary
                && oldItem.title == newItem.title
                && oldItem.year == newItem.year
    }
}