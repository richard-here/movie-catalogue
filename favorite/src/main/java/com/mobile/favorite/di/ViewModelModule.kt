package com.mobile.favorite.di

import com.mobile.favorite.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    factory(named("ioDispatcher")) { Dispatchers.IO }
    viewModel { FavoriteViewModel(get(), get(), get(named("ioDispatcher"))) }
}