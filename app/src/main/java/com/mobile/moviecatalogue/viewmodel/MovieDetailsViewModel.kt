package com.mobile.moviecatalogue.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mobile.moviecatalogue.di.IODispatcher
import com.mobile.core.domain.model.movie.Movie
import com.mobile.core.domain.model.movie.isEmptyMovie
import com.mobile.core.domain.usecase.movie.MovieUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieUseCase: MovieUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val movie: LiveData<Movie?>
        get() = _movie
    private var _movie: LiveData<Movie?> = MutableLiveData()

    val errorGettingMovie: MediatorLiveData<Boolean> = MediatorLiveData()

    val showFab: LiveData<Boolean>
        get() = _showFab
    private var _showFab = MutableLiveData(true)

    val isMovieFavorited: LiveData<Boolean>
        get() = _isMovieFavorited
    private var _isMovieFavorited: LiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch(ioDispatcher) {
            _movie = movieUseCase.getMovieById(id).asLiveData()
            _isMovieFavorited = movieUseCase.getIsMovieFave(id).asLiveData()
            errorGettingMovie.addSource(_movie) {
                errorGettingMovie.value =
                    it?.isEmptyMovie() ?: false
            }
        }
    }

    fun handleFavoriteFabClick(movie: Movie) {
        _showFab.value = false
        viewModelScope.launch(ioDispatcher) {
            _isMovieFavorited.value.also {
                when(it) {
                    true -> movieUseCase.deleteFavoriteMovie(id)
                    false -> movieUseCase.insertFavoriteMovie(movie).toInt()
                }
            }
            _showFab.postValue(true)
        }
    }
}