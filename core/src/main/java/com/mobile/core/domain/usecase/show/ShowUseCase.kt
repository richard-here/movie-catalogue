package com.mobile.core.domain.usecase.show

import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.ShowOverview
import kotlinx.coroutines.flow.Flow

interface ShowUseCase {
    fun getShowsLoadingState(): Flow<State>

    fun getShows(): Flow<PagedList<ShowOverview>>

    fun getFaveShows(): Flow<PagedList<ShowOverview>>

    fun getFaveShowsAlphaDesc(): Flow<PagedList<ShowOverview>>

    fun getFaveShowsChronoAsc(): Flow<PagedList<ShowOverview>>

    fun getFaveShowsChronoDesc(): Flow<PagedList<ShowOverview>>

    fun getIsShowFave(id: Int): Flow<Boolean>

    fun insertFavoriteShow(show: Show): Long

    fun deleteFavoriteShow(id: Int): Int

    suspend fun getShowById(id: Int): Flow<Show>
}