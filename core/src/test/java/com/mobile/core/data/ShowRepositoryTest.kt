package com.mobile.core.data

import com.mobile.core.dummydata.show.DummyShow
import com.mobile.core.dummydata.show.DummyShowOverview
import com.mobile.core.helper.PagedListHelper
import com.mobile.core.data.source.local.ShowLocalDataSource
import com.mobile.core.data.source.remote.ShowRemoteDataSource
import com.mobile.core.domain.model.State
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ShowRepositoryTest {
    private val localDataSourceMock = mockk<ShowLocalDataSource>()
    private val remoteDataSourceMock = mockk<ShowRemoteDataSource>()
    private val movieRepository = ShowRepository(localDataSourceMock, remoteDataSourceMock)

    @Before
    fun setup() {
        every { remoteDataSourceMock.getShowsLoadingState() } returns flow {
            emit(State.LOADING)
        }
        every { remoteDataSourceMock.getShows() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyShowOverview.list))
        }
        coEvery { remoteDataSourceMock.getShowById(any()) } returns flow {
            emit(DummyShow.data)
        }
        every { localDataSourceMock.getFaveShows() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyShowOverview.list))
        }
        every { localDataSourceMock.getIsShowFave(any()) } returns flow {
            emit(true)
        }
    }

    @Test
    fun ensure_getShowsLoadingState_gets_correct_loading_state() = runBlocking {
        val state = movieRepository.getShowsLoadingState().first()
        assertEquals(state, State.LOADING)
        verify { remoteDataSourceMock.getShowsLoadingState() }
    }

    @Test
    fun ensure_getShows_gets_the_right_movies() = runBlocking {
        val movies = movieRepository.getShows().first()
        assertEquals(movies.size, DummyShowOverview.list.size)
        assertEquals(movies[0], DummyShowOverview.list[0])
        verify { remoteDataSourceMock.getShows() }
    }

    @Test
    fun ensure_getShowById_gets_the_movie_with_the_right_id() = runBlocking {
        val movie = movieRepository.getShowById(0).first()
        assertEquals(movie.id, DummyShow.data.id)
        coVerify { remoteDataSourceMock.getShowById(0) }
    }

    @Test
    fun ensure_getFaveShows_gets_the_right_movies() = runBlocking {
        val movies = movieRepository.getFaveShows().first()
        assertEquals(movies.size, DummyShowOverview.list.size)
        assertEquals(movies[0], DummyShowOverview.list[0])
        verify { localDataSourceMock.getFaveShows() }
    }

    @Test
    fun ensure_getIsShowFave_returns_the_right_value() = runBlocking {
        val isShowFave = movieRepository.getIsShowFave(0).first()
        assertEquals(isShowFave, true)
        verify { localDataSourceMock.getIsShowFave(any()) }
    }
}