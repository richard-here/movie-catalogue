package com.mobile.core.data.source.remote.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.mobile.core.data.model.network.NetworkMovieOverview
import com.mobile.core.data.model.network.NetworkPopularMoviesResponse
import com.mobile.core.data.source.remote.service.MovieService
import com.mobile.core.domain.model.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PopularMoviePagedDataSource @Inject constructor(
    private val movieApi: MovieService
) : PageKeyedDataSource<Int, NetworkMovieOverview>() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    var state: MutableStateFlow<State> = MutableStateFlow(State.LOADING)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NetworkMovieOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = movieApi.getPopularMovies(page = 1)
                val movies = response.results
                callback.onResult(movies, getPreviousPage(response), getNextPage(response))
                updateState(State.DONE)
            } catch(e: Exception) {
                updateState(State.ERROR)
                Log.e(this.toString(), e.toString())
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NetworkMovieOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = movieApi.getPopularMovies(page = params.key)
                val movies = response.results
                callback.onResult(movies, getNextPage(response))
                updateState(State.DONE)
            } catch(e: Exception) {
                updateState(State.ERROR)
                Log.e(this.toString(), e.toString())
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NetworkMovieOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = movieApi.getPopularMovies(page = params.key)
                val movies = response.results
                callback.onResult(movies, getPreviousPage(response))
                updateState(State.DONE)
            } catch(e: Exception) {
                updateState(State.ERROR)
                Log.e(this.toString(), e.toString())
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    private fun updateState(state: State) {
        this.state.value = state
    }

    private fun getNextPage(response: NetworkPopularMoviesResponse) =
        if (response.page == response.totalPages) null
        else (response.page ?: 1) + 1

    private fun getPreviousPage(response: NetworkPopularMoviesResponse) =
        if (response.page == 1) null
        else (response.page ?: 1) + 1
}