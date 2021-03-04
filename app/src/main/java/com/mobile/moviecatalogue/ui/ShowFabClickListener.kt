package com.mobile.moviecatalogue.ui

import com.mobile.core.domain.model.show.Show

class ShowFabClickListener(val clickListener: (show: Show) -> Unit) {
    fun onClick(show: Show) = clickListener(show)
}