package com.mobile.moviecatalogue

import android.app.Application
import com.mobile.core.di.*
import com.mobile.core.di.koin.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class MovieCatalogueApplication : Application() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieCatalogueApplication)
            modules(
                listOf(
                    useCaseModule,
                    repositoryModule,
                    serviceModule,
                    databaseModule,
                    pagingModule,
                    daoModule,
                    dataSourceModule
                )
            )
        }
    }
}