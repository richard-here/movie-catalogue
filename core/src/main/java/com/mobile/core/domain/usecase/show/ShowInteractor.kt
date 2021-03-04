package com.mobile.core.domain.usecase.show

import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.domain.repository.show.IShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowInteractor @Inject constructor(private val showRepository: IShowRepository) : ShowUseCase {
    override fun getShowsLoadingState(): Flow<State> =
        showRepository.getShowsLoadingState()

    override fun getShows(): Flow<PagedList<ShowOverview>> =
        showRepository.getShows()

    override fun getFaveShows(): Flow<PagedList<ShowOverview>> =
        showRepository.getFaveShows()

    override fun getFaveShowsAlphaDesc(): Flow<PagedList<ShowOverview>> =
        showRepository.getFaveShowsAlphaDesc()

    override fun getFaveShowsChronoAsc(): Flow<PagedList<ShowOverview>> =
        showRepository.getFaveShowsChronoAsc()

    override fun getFaveShowsChronoDesc(): Flow<PagedList<ShowOverview>> =
        showRepository.getFaveShowsChronoDesc()

    override fun getIsShowFave(id: Int): Flow<Boolean> =
        showRepository.getIsShowFave(id)

    override fun insertFavoriteShow(show: Show): Long =
        showRepository.insertFavoriteShow(show)

    override fun deleteFavoriteShow(id: Int): Int =
        showRepository.deleteFavoriteShow(id)

    override suspend fun getShowById(id: Int): Flow<Show> =
        showRepository.getShowById(id)
}