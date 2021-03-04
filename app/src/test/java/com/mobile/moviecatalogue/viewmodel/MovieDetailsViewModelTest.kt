package com.mobile.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.moviecatalogue.helper.LiveDataHelper.getOrAwaitValue
import io.mockk.coEvery
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var movieUseCaseMock: MovieUseCase
    private lateinit var savedStateHandleMock: SavedStateHandle
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val mockMovie = mockk<Movie>()
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        movieUseCaseMock = mockk(relaxed = true)
        savedStateHandleMock = mockk()
        every { savedStateHandleMock.get<Int>(any()) } returns 0
        coEvery { movieUseCaseMock.getMovieById(any()) } returns flow { emit(mockMovie) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun ensure_handleFavoriteFabClick_unfavorites_movie() = runBlocking {
        every { movieUseCaseMock.getIsMovieFave(any()) } returns flow { emit(true) }
        movieDetailsViewModel = MovieDetailsViewModel(movieUseCaseMock, savedStateHandleMock, dispatcher)
        assertEquals(movieDetailsViewModel.isMovieFavorited.getOrAwaitValue(), true)
        movieDetailsViewModel.handleFavoriteFabClick(mockMovie)
        verify {
            movieUseCaseMock.deleteFavoriteMovie(any())
        }
    }

    @Test
    fun ensure_handleFavoriteFabClick_favorites_movie() = runBlocking {
        every { movieUseCaseMock.getIsMovieFave(any()) } returns flow { emit(false) }
        movieDetailsViewModel = MovieDetailsViewModel(movieUseCaseMock, savedStateHandleMock, dispatcher)
        assertEquals(movieDetailsViewModel.isMovieFavorited.getOrAwaitValue(), false)
        movieDetailsViewModel.handleFavoriteFabClick(mockMovie)
        verify {
            movieUseCaseMock.insertFavoriteMovie(any())
        }
    }
}