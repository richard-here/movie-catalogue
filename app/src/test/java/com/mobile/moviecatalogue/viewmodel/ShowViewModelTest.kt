package com.mobile.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mobile.moviecatalogue.dummydata.show.DummyShowOverview
import com.mobile.core.domain.model.State
import com.mobile.core.domain.usecase.show.ShowUseCase
import com.mobile.moviecatalogue.helper.LiveDataHelper.getOrAwaitValue
import com.mobile.moviecatalogue.helper.PagedListHelper
import io.mockk.every
import io.mockk.mockk
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
class ShowViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var showUseCaseMock: ShowUseCase
    private lateinit var showViewModel: ShowViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        showUseCaseMock = mockk(relaxed = true)
        showViewModel = ShowViewModel(showUseCaseMock, dispatcher)
        every { showUseCaseMock.getShowsLoadingState() } returns flow {
            emit(State.LOADING)
        }
        every { showUseCaseMock.getShows() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyShowOverview.list))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun ensure_startNavigatingToDetails_navigate_to_the_right_movie() = runBlocking {
        showViewModel.startNavigatingToDetails(1)
        assertEquals(showViewModel.navigateToDetails.getOrAwaitValue(), 1)
    }

    @Test
    fun ensure_finishNavigatingToDetails_resets_navigate_value() = runBlocking {
        showViewModel.finishNavigatingToDetails()
        assertEquals(showViewModel.navigateToDetails.getOrAwaitValue(), -1)
    }
}