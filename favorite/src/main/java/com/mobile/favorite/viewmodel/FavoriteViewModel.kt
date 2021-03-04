package com.mobile.favorite.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.core.domain.usecase.show.ShowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieUseCase: MovieUseCase,
    private val showUseCase: ShowUseCase,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val navigateToDetails: LiveData<Pair<Int, String>>
        get() = _navigateToDetails
    private var _navigateToDetails: MutableLiveData<Pair<Int, String>> = MutableLiveData()

    private var movieSortConfig = MutableLiveData(SortConfig.ALPHABETICALLY_ASCENDING)
    private var showSortConfig = MutableLiveData(SortConfig.ALPHABETICALLY_ASCENDING)

    var movieList: LiveData<PagedList<MovieOverview>> = MutableLiveData()
    val isMovieEmpty: LiveData<Boolean>
        get() = _isMovieEmpty
    private var _isMovieEmpty = MutableLiveData(false)

    var showList: LiveData<PagedList<ShowOverview>> = MutableLiveData()
    val isShowEmpty: LiveData<Boolean>
        get() = _isShowEmpty
    private var _isShowEmpty = MutableLiveData(false)

    init {
        viewModelScope.launch(ioDispatcher) {
            this@FavoriteViewModel.movieList =
                Transformations.switchMap(movieSortConfig) { sortConfig ->
                    when (sortConfig) {
                        SortConfig.ALPHABETICALLY_DESCENDING ->
                            movieUseCase.getFaveMoviesAlphaDesc().asLiveData()

                        SortConfig.CHRONOLOGICALLY_ASCENDING ->
                            movieUseCase.getFaveMoviesChronoAsc().asLiveData()

                        SortConfig.CHRONOLOGICALLY_DESCENDING ->
                            movieUseCase.getFaveMoviesChronoDesc().asLiveData()

                        else ->
                            movieUseCase.getFaveMovies().asLiveData()
                    }
                }
        }

        viewModelScope.launch(ioDispatcher) {
            this@FavoriteViewModel.showList = Transformations.switchMap(showSortConfig) { sortConfig ->
                when (sortConfig) {
                    SortConfig.ALPHABETICALLY_DESCENDING ->
                        showUseCase.getFaveShowsAlphaDesc().asLiveData()

                    SortConfig.CHRONOLOGICALLY_ASCENDING ->
                        showUseCase.getFaveShowsChronoAsc().asLiveData()

                    SortConfig.CHRONOLOGICALLY_DESCENDING ->
                        showUseCase.getFaveShowsChronoDesc().asLiveData()

                    else ->
                        showUseCase.getFaveShows().asLiveData()
                }
            }
        }
    }

    fun setMovieEmptyState(isEmpty: Boolean) {
        _isMovieEmpty.value = isEmpty
    }

    fun setShowEmptyState(isEmpty: Boolean) {
        _isShowEmpty.value = isEmpty
    }

    fun setMovieSortConfig(config: SortConfig) {
        movieSortConfig.value = config
    }

    fun setShowSortConfig(config: SortConfig) {
        showSortConfig.value = config
    }

    fun startNavigatingToDetails(id: Int, type: String) {
        _navigateToDetails.value = Pair(id, type)
    }

    fun finishNavigatingToDetails() {
        _navigateToDetails.value = Pair(-1, "")
    }

    companion object {
        enum class SortConfig {
            ALPHABETICALLY_ASCENDING,
            ALPHABETICALLY_DESCENDING,
            CHRONOLOGICALLY_ASCENDING,
            CHRONOLOGICALLY_DESCENDING
        }
    }
}