package com.mobile.core.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.mobile.core.domain.model.show.ShowOverview

class ShowOverviewDiffCallback : DiffUtil.ItemCallback<ShowOverview>() {
    override fun areItemsTheSame(
        oldItem: ShowOverview,
        newItem: ShowOverview
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ShowOverview,
        newItem: ShowOverview
    ): Boolean {
        return oldItem.coverUrl == newItem.coverUrl
                && oldItem.firstAired == newItem.firstAired
                && oldItem.rating == newItem.rating
                && oldItem.summary == newItem.summary
                && oldItem.title == newItem.title
    }
}