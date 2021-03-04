package com.mobile.core.data

import com.mobile.core.dummydata.movie.DummyMovie
import com.mobile.core.dummydata.movie.DummyMovieOverview
import com.mobile.core.helper.PagedListHelper
import com.mobile.core.data.source.local.MovieLocalDataSource
import com.mobile.core.data.source.remote.MovieRemoteDataSource
import com.mobile.core.domain.model.State
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {
    private val localDataSourceMock = mockk<MovieLocalDataSource>()
    private val remoteDataSourceMock = mockk<MovieRemoteDataSource>()
    private val movieRepository = MovieRepository(localDataSourceMock, remoteDataSourceMock)

    @Before
    fun setup() {
        every { remoteDataSourceMock.getMoviesLoadingState() } returns flow {
            emit(State.LOADING)
        }
        every { remoteDataSourceMock.getMovies() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyMovieOverview.list))
        }
        coEvery { remoteDataSourceMock.getMovieById(any()) } returns flow {
            emit(DummyMovie.data)
        }
        every { localDataSourceMock.getFaveMovies() } returns flow {
            emit(PagedListHelper.mockPagedList(DummyMovieOverview.list))
        }
        every { localDataSourceMock.getIsMovieFave(any()) } returns flow {
            emit(true)
        }
    }

    @Test
    fun ensure_getMoviesLoadingState_gets_correct_loading_state() = runBlocking {
        val state = movieRepository.getMoviesLoadingState().first()
        assertEquals(state, State.LOADING)
        verify { remoteDataSourceMock.getMoviesLoadingState() }
    }

    @Test
    fun ensure_getMovies_gets_the_right_movies() = runBlocking {
        val movies = movieRepository.getMovies().first()
        assertEquals(movies.size, DummyMovieOverview.list.size)
        assertEquals(movies[0], DummyMovieOverview.list[0])
        verify { remoteDataSourceMock.getMovies() }
    }

    @Test
    fun ensure_getMovieById_gets_the_movie_with_the_right_id() = runBlocking {
        val movie = movieRepository.getMovieById(0).first()
        assertEquals(movie.id, DummyMovie.data.id)
        coVerify { remoteDataSourceMock.getMovieById(0) }
    }

    @Test
    fun ensure_getFaveMovies_gets_the_right_movies() = runBlocking {
        val movies = movieRepository.getFaveMovies().first()
        assertEquals(movies.size, DummyMovieOverview.list.size)
        assertEquals(movies[0], DummyMovieOverview.list[0])
        verify { localDataSourceMock.getFaveMovies() }
    }

    @Test
    fun ensure_getIsMovieFave_returns_the_right_value() = runBlocking {
        val isMovieFave = movieRepository.getIsMovieFave(0).first()
        assertEquals(isMovieFave, true)
        verify { localDataSourceMock.getIsMovieFave(any()) }
    }
}