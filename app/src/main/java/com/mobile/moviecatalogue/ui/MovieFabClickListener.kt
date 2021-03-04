package com.mobile.moviecatalogue.ui

import com.mobile.core.domain.model.movie.Movie

class MovieFabClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}