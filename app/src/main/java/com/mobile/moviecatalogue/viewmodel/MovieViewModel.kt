package com.mobile.moviecatalogue.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.moviecatalogue.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieViewModel @ViewModelInject constructor(
    private val movieUseCase: MovieUseCase,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val navigateToDetails: LiveData<Int>
        get() = _navigateToDetails
    private var _navigateToDetails: MutableLiveData<Int> = MutableLiveData()

    val movieList: LiveData<PagedList<MovieOverview>>
        get() = _movieList
    private var _movieList: LiveData<PagedList<MovieOverview>> = MutableLiveData()

    private val state: LiveData<State> = movieUseCase.getMoviesLoadingState().asLiveData()
    val errorLoading: LiveData<Boolean>
        get() = state.map { it == State.ERROR }
    val isLoading: LiveData<Boolean>
        get() = state.map { it == State.LOADING }

    init {
        viewModelScope.launch(ioDispatcher) {
            _movieList = movieUseCase.getMovies().asLiveData()
        }
    }

    fun startNavigatingToDetails(id: Int) {
        _navigateToDetails.value = id
    }

    fun finishNavigatingToDetails() {
        _navigateToDetails.value = -1
    }
}