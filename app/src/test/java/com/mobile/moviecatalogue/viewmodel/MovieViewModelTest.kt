package com.mobile.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mobile.moviecatalogue.dummydata.movie.DummyMovieOverview
import com.mobile.core.domain.model.State
import com.mobile.core.domain.usecase.movie.MovieUseCase
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
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var movieUseCaseMock: MovieUseCase
    private lateinit var movieViewModel: MovieViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        movieUseCaseMock = mockk(relaxed = true)
        movieViewModel = MovieViewModel(movieUseCaseMock, dispatcher)
        every { movieUseCaseMock.getMoviesLoadingState() } returns flow {
            emit(State.LOADING)
        }
        every { movieUseCaseMock.getMovies() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyMovieOverview.list))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun ensure_startNavigatingToDetails_navigate_to_the_right_movie() = runBlocking {
        movieViewModel.startNavigatingToDetails(1)
        assertEquals(movieViewModel.navigateToDetails.getOrAwaitValue(), 1)
    }

    @Test
    fun ensure_finishNavigatingToDetails_resets_navigate_value() = runBlocking {
        movieViewModel.finishNavigatingToDetails()
        assertEquals(movieViewModel.navigateToDetails.getOrAwaitValue(), -1)
    }
}