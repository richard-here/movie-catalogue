package com.mobile.core.di.koin

import androidx.room.Room
import com.mobile.core.data.MovieRepository
import com.mobile.core.data.ShowRepository
import com.mobile.core.data.source.local.MovieLocalDataSource
import com.mobile.core.data.source.local.ShowLocalDataSource
import com.mobile.core.data.source.local.room.RoomDB
import com.mobile.core.data.source.remote.MovieRemoteDataSource
import com.mobile.core.data.source.remote.ShowRemoteDataSource
import com.mobile.core.data.source.remote.paging.PopularMoviePagedDataSource
import com.mobile.core.data.source.remote.paging.PopularShowPagedDataSource
import com.mobile.core.data.source.remote.service.MovieService
import com.mobile.core.data.source.remote.service.RetrofitService
import com.mobile.core.data.source.remote.service.ShowService
import com.mobile.core.domain.repository.movie.IMovieRepository
import com.mobile.core.domain.repository.show.IShowRepository
import com.mobile.core.domain.usecase.movie.MovieInteractor
import com.mobile.core.domain.usecase.movie.MovieUseCase
import com.mobile.core.domain.usecase.show.ShowInteractor
import com.mobile.core.domain.usecase.show.ShowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Untuk digunakan pada Dynamic Feature Module Favorite
 * karena Hilt belum dapat digunakan secara langsung
 * dengan DFM
 */
val daoModule = module {
    factory { get<RoomDB>().favoriteMovieDao() }
    factory { get<RoomDB>().favoriteShowDao() }
}
@ExperimentalCoroutinesApi
val pagingModule = module {
    factory { PopularShowPagedDataSource(get()) }
    factory { PopularMoviePagedDataSource(get()) }
}
val databaseModule = module {
    factory {
        val passphrase = SQLiteDatabase.getBytes("secretKey".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            RoomDB::class.java,
            RoomDB.DATABASE_NAME
        ).openHelperFactory(factory)
            .build()
    }
}
@ExperimentalCoroutinesApi
val serviceModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        if (RetrofitService.retrofit == null) {
            RetrofitService.retrofit = Retrofit.Builder()
                .client(get())
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        RetrofitService.retrofit as Retrofit
    }
    factory<ShowService> { get<Retrofit>().create(ShowService::class.java) }
    factory<MovieService> { get<Retrofit>().create(MovieService::class.java) }
}
@ExperimentalCoroutinesApi
val repositoryModule = module {
    single<IMovieRepository> { MovieRepository(get(), get()) }
    single<IShowRepository> { ShowRepository(get(), get()) }
}
val useCaseModule = module {
    factory<ShowUseCase> { ShowInteractor(get()) }
    factory<MovieUseCase> { MovieInteractor(get()) }
}
@ExperimentalCoroutinesApi
val dataSourceModule = module {
    single { ShowLocalDataSource(get()) }
    single { ShowRemoteDataSource(get(), get()) }
    single { MovieLocalDataSource(get()) }
    single { MovieRemoteDataSource(get(), get()) }
}