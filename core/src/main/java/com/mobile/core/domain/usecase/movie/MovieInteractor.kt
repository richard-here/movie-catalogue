package com.mobile.core.domain.usecase.movie

import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.repository.movie.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getMoviesLoadingState(): Flow<State> =
        movieRepository.getMoviesLoadingState()

    override fun getMovies(): Flow<PagedList<MovieOverview>> =
        movieRepository.getMovies()

    override fun getFaveMovies(): Flow<PagedList<MovieOverview>> =
        movieRepository.getFaveMovies()

    override fun getFaveMoviesAlphaDesc(): Flow<PagedList<MovieOverview>> =
        movieRepository.getFaveMoviesAlphaDesc()

    override fun getFaveMoviesChronoAsc(): Flow<PagedList<MovieOverview>> =
        movieRepository.getFaveMoviesChronoAsc()

    override fun getFaveMoviesChronoDesc(): Flow<PagedList<MovieOverview>> =
        movieRepository.getFaveMoviesChronoDesc()

    override fun getIsMovieFave(id: Int): Flow<Boolean> =
        movieRepository.getIsMovieFave(id)

    override fun insertFavoriteMovie(movie: Movie): Long =
        movieRepository.insertFavoriteMovie(movie)

    override fun deleteFavoriteMovie(id: Int): Int =
        movieRepository.deleteFavoriteMovie(id)

    override suspend fun getMovieById(id: Int): Flow<Movie> =
        movieRepository.getMovieById(id)
}