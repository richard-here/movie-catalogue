package com.mobile.core.data

import androidx.paging.PagedList
import com.mobile.core.data.source.local.ShowLocalDataSource
import com.mobile.core.data.source.remote.ShowRemoteDataSource
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.domain.repository.show.IShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ShowRepository @Inject constructor(
    private val localDataSource: ShowLocalDataSource,
    private val remoteDataSource: ShowRemoteDataSource
) : IShowRepository {
    override fun getShowsLoadingState(): Flow<State> = remoteDataSource.getShowsLoadingState()

    override fun getShows(): Flow<PagedList<ShowOverview>> = remoteDataSource.getShows()

    override suspend fun getShowById(id: Int): Flow<Show> = remoteDataSource.getShowById(id)

    override fun getFaveShows(): Flow<PagedList<ShowOverview>> = localDataSource.getFaveShows()

    override fun getFaveShowsAlphaDesc(): Flow<PagedList<ShowOverview>> = localDataSource.getFaveShowsAlphaDesc()

    override fun getFaveShowsChronoAsc(): Flow<PagedList<ShowOverview>> = localDataSource.getFaveShowsChronoAsc()

    override fun getFaveShowsChronoDesc(): Flow<PagedList<ShowOverview>> = localDataSource.getFaveShowsChronoDesc()

    override fun getIsShowFave(id: Int): Flow<Boolean> = localDataSource.getIsShowFave(id)

    override fun insertFavoriteShow(show: Show): Long = localDataSource.insertFavoriteShow(show)

    override fun deleteFavoriteShow(id: Int): Int = localDataSource.deleteFavoriteShow(id)
}