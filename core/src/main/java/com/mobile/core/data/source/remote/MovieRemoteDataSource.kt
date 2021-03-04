package com.mobile.core.data.source.remote

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mobile.core.data.source.remote.paging.PopularMoviePagedDataSource
import com.mobile.core.data.source.remote.service.MovieService
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.utils.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieService,
    private val moviePagedDataSourceLiveData: PopularMoviePagedDataSource
) {
    fun getMoviesLoadingState(): Flow<State> = moviePagedDataSourceLiveData.state

    fun getMovies(): Flow<PagedList<MovieOverview>> {
        val dataSourceFactory = object : DataSource.Factory<Int, MovieOverview>() {
            override fun create(): DataSource<Int, MovieOverview> {
                return moviePagedDataSourceLiveData.map{
                    it.toDomainModel()
                }
            }
        }
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build().asFlow()
    }

    suspend fun getMovieById(id: Int): Flow<Movie> {
        return flow {
            try {
                emit(movieApi.getMovieById(id).toDomainModel())
            } catch(e: Exception) {
                Log.e("getMovieById", e.toString())
                emit(Movie())
            } finally {
            }
        }
    }

    companion object {
        private val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(1)
            .build()
    }
}