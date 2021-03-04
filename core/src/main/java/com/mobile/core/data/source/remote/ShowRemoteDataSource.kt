package com.mobile.core.data.source.remote

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mobile.core.data.source.remote.paging.PopularShowPagedDataSource
import com.mobile.core.data.source.remote.service.ShowService
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.utils.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ShowRemoteDataSource @Inject constructor(
    private val showApi: ShowService,
    private val showPagedDataSource: PopularShowPagedDataSource,
) {
    fun getShowsLoadingState(): Flow<State> = showPagedDataSource.state

    fun getShows(): Flow<PagedList<ShowOverview>> {
        val dataSourceFactory = object : DataSource.Factory<Int, ShowOverview>() {
            override fun create(): DataSource<Int, ShowOverview> {
                return showPagedDataSource.map {
                    it.toDomainModel()
                }
            }
        }
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build().asFlow()
    }

    suspend fun getShowById(id: Int): Flow<Show> {
        return flow {
            try {
                emit(showApi.getShowById(id).toDomainModel())
            } catch(e: Exception) {
                Log.e("getShowById", e.toString())
                emit(Show())
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