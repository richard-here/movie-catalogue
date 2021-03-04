package com.mobile.core.domain.usecase.movie

import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.MovieOverview
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMoviesLoadingState(): Flow<State>

    fun getMovies(): Flow<PagedList<MovieOverview>>

    fun getFaveMovies(): Flow<PagedList<MovieOverview>>

    fun getFaveMoviesAlphaDesc(): Flow<PagedList<MovieOverview>>

    fun getFaveMoviesChronoAsc(): Flow<PagedList<MovieOverview>>

    fun getFaveMoviesChronoDesc(): Flow<PagedList<MovieOverview>>

    fun getIsMovieFave(id: Int): Flow<Boolean>

    fun insertFavoriteMovie(movie: Movie): Long

    fun deleteFavoriteMovie(id: Int): Int

    suspend fun getMovieById(id: Int): Flow<Movie>
}