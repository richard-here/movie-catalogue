package com.mobile.core.data.source.local

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mobile.core.data.source.local.dao.FavoriteShowDao
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.utils.toDataModel
import com.mobile.core.utils.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowLocalDataSource @Inject constructor(
    private val favoriteShowDao: FavoriteShowDao
) {
    fun getFaveShows(): Flow<PagedList<ShowOverview>> {
        val result = LivePagedListBuilder(
            favoriteShowDao.getFavoriteShows().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveShowsAlphaDesc(): Flow<PagedList<ShowOverview>> {
        val result = LivePagedListBuilder(
            favoriteShowDao.getFavoriteShowsAlphabeticallyDescending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveShowsChronoAsc(): Flow<PagedList<ShowOverview>> {
        val result = LivePagedListBuilder(
            favoriteShowDao.getFavoriteShowsChronologicallyAscending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getFaveShowsChronoDesc(): Flow<PagedList<ShowOverview>> {
        val result = LivePagedListBuilder(
            favoriteShowDao.getFavoriteShowsChronologicallyDescending().map {
                it.toDomainModel()
            },
            pagedListConfig
        ).build()
        return result.asFlow()
    }

    fun getIsShowFave(id: Int): Flow<Boolean> =
        favoriteShowDao.getFavoriteShowById(id)
            .map {
                it != null
            }

    fun insertFavoriteShow(show: Show): Long =
        favoriteShowDao.insertFavoriteShow(show.toDataModel())

    fun deleteFavoriteShow(id: Int): Int =
        favoriteShowDao.deleteFavoriteShow(id)

    companion object {
        private val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .setPrefetchDistance(1)
            .build()
    }
}