package com.mobile.core.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.mobile.core.domain.model.Genre

class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(
        oldItem: Genre,
        newItem: Genre
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Genre,
        newItem: Genre
    ): Boolean {
        return oldItem.name == newItem.name
    }
}