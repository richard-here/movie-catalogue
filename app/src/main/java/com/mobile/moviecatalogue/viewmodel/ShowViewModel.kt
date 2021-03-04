package com.mobile.moviecatalogue.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.mobile.core.domain.model.State
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.domain.usecase.show.ShowUseCase
import com.mobile.moviecatalogue.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ShowViewModel @ViewModelInject constructor(
    private val showUseCase: ShowUseCase,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val navigateToDetails: LiveData<Int>
        get() = _navigateToDetails
    private var _navigateToDetails: MutableLiveData<Int> = MutableLiveData()

    val showList: LiveData<PagedList<ShowOverview>>
        get() = _showList
    private var _showList: LiveData<PagedList<ShowOverview>> = MutableLiveData()

    private val state: LiveData<State> = showUseCase.getShowsLoadingState().asLiveData()
    val errorLoading: LiveData<Boolean>
        get() = Transformations.map(state) { it == State.ERROR }
    val isLoading: LiveData<Boolean>
        get() = Transformations.map(state) { it == State.LOADING }

    init {
        viewModelScope.launch(ioDispatcher) {
            _showList = showUseCase.getShows().asLiveData()
        }
    }

    fun startNavigatingToDetails(id: Int) {
        _navigateToDetails.value = id
    }

    fun finishNavigatingToDetails() {
        _navigateToDetails.value = -1
    }
}