package com.mobile.core.data

import androidx.paging.PagedList
import com.mobile.core.data.source.local.MovieLocalDataSource
import com.mobile.core.data.source.remote.MovieRemoteDataSource
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.repository.movie.IMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) : IMovieRepository {
    override fun getMoviesLoadingState(): Flow<State> = remoteDataSource.getMoviesLoadingState()

    override fun getMovies(): Flow<PagedList<MovieOverview>> = remoteDataSource.getMovies()

    override suspend fun getMovieById(id: Int): Flow<Movie> = remoteDataSource.getMovieById(id)

    override fun getFaveMovies(): Flow<PagedList<MovieOverview>> = localDataSource.getFaveMovies()

    override fun getFaveMoviesAlphaDesc(): Flow<PagedList<MovieOverview>> = localDataSource.getFaveMoviesAlphaDesc()

    override fun getFaveMoviesChronoAsc(): Flow<PagedList<MovieOverview>> = localDataSource.getFaveMoviesChronoAsc()

    override fun getFaveMoviesChronoDesc(): Flow<PagedList<MovieOverview>> = localDataSource.getFaveMoviesChronoDesc()

    override fun getIsMovieFave(id: Int): Flow<Boolean> = localDataSource.getIsMovieFave(id)

    override fun insertFavoriteMovie(movie: Movie): Long = localDataSource.insertFavoriteMovie(movie)

    override fun deleteFavoriteMovie(id: Int): Int = localDataSource.deleteFavoriteMovie(id)
}