package com.mobile.moviecatalogue.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.show.isEmptyShow
import com.mobile.core.domain.usecase.show.ShowUseCase
import com.mobile.moviecatalogue.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ShowDetailsViewModel @ViewModelInject constructor(
    private val showUseCase: ShowUseCase,
    @Assisted savedStateHandle: SavedStateHandle,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val show: LiveData<Show?>
        get() = _show
    private var _show: LiveData<Show?> = MutableLiveData()

    val errorGettingShow: MediatorLiveData<Boolean> = MediatorLiveData()

    val showFab: LiveData<Boolean>
        get() = _showFab
    private var _showFab = MutableLiveData(true)

    val isShowFavorited: LiveData<Boolean>
        get() = _isShowFavorited
    private var _isShowFavorited: LiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch(ioDispatcher) {
            _show = showUseCase.getShowById(id).asLiveData()
            _isShowFavorited = showUseCase.getIsShowFave(id).asLiveData()
            errorGettingShow.addSource(_show) {
                errorGettingShow.value =
                    it?.isEmptyShow() ?: false
            }
        }
    }

    fun handleFavoriteFabClick(show: Show) {
        _showFab.value = false
        viewModelScope.launch(ioDispatcher) {
            isShowFavorited.value.also {
                when(it) {
                    true -> showUseCase.deleteFavoriteShow(id)
                    false -> showUseCase.insertFavoriteShow(show).toInt()
                }
            }
            _showFab.postValue(true)
        }
    }
}