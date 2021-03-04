package com.mobile.moviecatalogue.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.usecase.show.ShowUseCase
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
class ShowDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var showUseCaseMock: ShowUseCase
    private lateinit var savedStateHandleMock: SavedStateHandle
    private lateinit var showDetailsViewModel: ShowDetailsViewModel
    private val mockShow = mockk<Show>()
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        showUseCaseMock = mockk(relaxed = true)
        savedStateHandleMock = mockk()
        every { savedStateHandleMock.get<Int>(any()) } returns 0
        coEvery { showUseCaseMock.getShowById(any()) } returns flow { emit(mockShow) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun ensure_handleFavoriteFabClick_unfavorites_show() = runBlocking {
        every { showUseCaseMock.getIsShowFave(any()) } returns flow { emit(true) }
        showDetailsViewModel = ShowDetailsViewModel(showUseCaseMock, savedStateHandleMock, dispatcher)
        assertEquals(showDetailsViewModel.isShowFavorited.getOrAwaitValue(), true)
        showDetailsViewModel.handleFavoriteFabClick(mockShow)
        verify {
            showUseCaseMock.deleteFavoriteShow(any())
        }
    }

    @Test
    fun ensure_handleFavoriteFabClick_favorites_show() = runBlocking {
        every { showUseCaseMock.getIsShowFave(any()) } returns flow { emit(false) }
        showDetailsViewModel = ShowDetailsViewModel(showUseCaseMock, savedStateHandleMock, dispatcher)
        assertEquals(showDetailsViewModel.isShowFavorited.getOrAwaitValue(), false)
        showDetailsViewModel.handleFavoriteFabClick(mockShow)
        verify {
            showUseCaseMock.insertFavoriteShow(any())
        }
    }
}