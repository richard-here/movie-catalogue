package com.mobile.core.data.source.local

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mobile.core.data.source.local.dao.FavoriteMovieDao
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.utils.toDataModel
import com.mobile.core.utils.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    fun getFaveMovies(): Flow<PagedList<MovieOverview>> {
        val result = LivePagedListBuilder(
            favoriteMovieDao.getFavoriteMovies().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveMoviesAlphaDesc(): Flow<PagedList<MovieOverview>> {
        val result = LivePagedListBuilder(
            favoriteMovieDao.getFavoriteMoviesAlphabeticallyDescending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveMoviesChronoAsc(): Flow<PagedList<MovieOverview>> {
        val result = LivePagedListBuilder(
            favoriteMovieDao.getFavoriteMoviesChronologicallyAscending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveMoviesChronoDesc(): Flow<PagedList<MovieOverview>> {
        val result = LivePagedListBuilder(
            favoriteMovieDao.getFavoriteMoviesChronologicallyDescending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getIsMovieFave(id: Int): Flow<Boolean> =
        favoriteMovieDao.getFavoriteMovieById(id)
            .map {
                it != null
            }

    fun insertFavoriteMovie(movie: Movie): Long =
        favoriteMovieDao.insertFavoriteMovie(movie.toDataModel())

    fun deleteFavoriteMovie(id: Int): Int =
        favoriteMovieDao.deleteFavoriteMovie(id)

    companion object {
        private val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(1)
            .build()
    }
}