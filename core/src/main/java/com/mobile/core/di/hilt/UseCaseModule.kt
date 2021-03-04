package com.mobile.core.di.hilt

import com.mobile.core.data.MovieRepository
import com.mobile.core.data.ShowRepository
import com.mobile.core.domain.usecase.movie.MovieInteractor
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.core.domain.usecase.show.ShowInteractor
import com.mobile.core.domain.usecase.show.ShowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
@ExperimentalCoroutinesApi
class UseCaseModule {
    @Provides
    fun provideMovieUseCase(movieRepository: MovieRepository): MovieUseCase {
        return MovieInteractor(movieRepository)
    }

    @Provides
    fun provideShowUseCase(showRepository: ShowRepository): ShowUseCase {
        return ShowInteractor(showRepository)
    }
}