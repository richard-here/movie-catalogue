package com.mobile.core.data.source.remote.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.mobile.core.data.model.network.NetworkPopularShowsResponse
import com.mobile.core.data.model.network.NetworkShowOverview
import com.mobile.core.data.source.remote.service.ShowService
import com.mobile.core.domain.model.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PopularShowPagedDataSource @Inject constructor(
    private val showApi: ShowService
) : PageKeyedDataSource<Int, NetworkShowOverview>() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    var state = MutableStateFlow(State.LOADING)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NetworkShowOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = showApi.getPopularShows(page = 1)
                val shows = response.results
                callback.onResult(shows, getPreviousPage(response), getNextPage(response))
                updateState(State.DONE)
            } catch(e: Exception) {
                updateState(State.ERROR)
                Log.e(this.toString(), e.toString())
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NetworkShowOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = showApi.getPopularShows(page = params.key)
                val shows = response.results
                callback.onResult(shows, getNextPage(response))
                updateState(State.DONE)
            } catch(e: Exception) {
                updateState(State.ERROR)
                Log.e(this.toString(), e.toString())
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, NetworkShowOverview>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = showApi.getPopularShows(page = params.key)
                val shows = response.results
                callback.onResult(shows, getPreviousPage(response))
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

    private fun getNextPage(response: NetworkPopularShowsResponse) =
        if (response.page == response.totalPages) null
        else (response.page ?: 1) + 1

    private fun getPreviousPage(response: NetworkPopularShowsResponse) =
        if (response.page == 1) null
        else (response.page ?: 1) + 1
}