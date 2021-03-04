package com.mobile.favorite.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.core.domain.usecase.show.ShowUseCase
import com.mobile.favorite.dummydata.movie.DummyMovieOverview
import com.mobile.favorite.dummydata.show.DummyShowOverview
import com.mobile.favorite.helper.LiveDataHelper.getOrAwaitValue
import com.mobile.favorite.helper.PagedListHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var movieUseCaseMock: MovieUseCase
    private lateinit var showUseCaseMock: ShowUseCase
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        showUseCaseMock = mockk(relaxed = true)
        movieUseCaseMock = mockk(relaxed = true)
        favoriteViewModel = FavoriteViewModel(movieUseCaseMock, showUseCaseMock, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun ensure_setMovieEmptyState_works() {
        favoriteViewModel.setMovieEmptyState(true)
        assertEquals(favoriteViewModel.isMovieEmpty.getOrAwaitValue(), true)
    }

    @Test
    fun ensure_setShowEmptyState_works() {
        favoriteViewModel.setShowEmptyState(true)
        assertEquals(favoriteViewModel.isShowEmpty.getOrAwaitValue(), true)
    }

    @Test
    fun ensure_setShowSortConfig_works() = runBlocking {
        favoriteViewModel.setShowSortConfig(FavoriteViewModel.Companion.SortConfig.CHRONOLOGICALLY_ASCENDING)
        every { showUseCaseMock.getFaveShowsChronoAsc() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyShowOverview.list))
        }
        favoriteViewModel.showList.getOrAwaitValue()
        verify {
            showUseCaseMock.getFaveShowsChronoAsc()
        }
    }

    @Test
    fun ensure_setMovieSortConfig_works() = runBlocking {
        favoriteViewModel.setMovieSortConfig(FavoriteViewModel.Companion.SortConfig.ALPHABETICALLY_DESCENDING)
        every { movieUseCaseMock.getFaveMoviesAlphaDesc() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyMovieOverview.list))
        }
        favoriteViewModel.movieList.getOrAwaitValue()
        verify {
            movieUseCaseMock.getFaveMoviesAlphaDesc()
        }
    }

    @Test
    fun ensure_startNavigatingToDetails_works() {
        favoriteViewModel.startNavigatingToDetails(0, "dummy")
        assertEquals(favoriteViewModel.navigateToDetails.getOrAwaitValue(), Pair(0, "dummy"))
    }

    @Test
    fun ensure_finishNavigatingToDetails_works() {
        favoriteViewModel.finishNavigatingToDetails()
        assertEquals(favoriteViewModel.navigateToDetails.getOrAwaitValue(), Pair(-1, ""))
    }
}