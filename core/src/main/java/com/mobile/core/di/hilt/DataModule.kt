package com.mobile.core.di.hilt

import android.content.Context
import androidx.room.Room
import com.mobile.core.data.MovieRepository
import com.mobile.core.data.ShowRepository
import com.mobile.core.data.source.local.MovieLocalDataSource
import com.mobile.core.data.source.local.ShowLocalDataSource
import com.mobile.core.data.source.local.dao.FavoriteMovieDao
import com.mobile.core.data.source.local.dao.FavoriteShowDao
import com.mobile.core.data.source.local.room.RoomDB
import com.mobile.core.data.source.remote.MovieRemoteDataSource
import com.mobile.core.data.source.remote.ShowRemoteDataSource
import com.mobile.core.data.source.remote.service.MovieService
import com.mobile.core.data.source.remote.service.RetrofitService
import com.mobile.core.data.source.remote.service.ShowService
import com.mobile.core.domain.repository.movie.IMovieRepository
import com.mobile.core.domain.repository.show.IShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
@ExperimentalCoroutinesApi
class DataModule {
    @Provides
    fun provideFavoriteMovieDao(database: RoomDB): FavoriteMovieDao
            = database.favoriteMovieDao()

    @Provides
    fun provideFavoriteShowDao(database: RoomDB): FavoriteShowDao
            = database.favoriteShowDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext appContext: Context): RoomDB {
        val passphrase = SQLiteDatabase.getBytes("secretKey".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            appContext,
            RoomDB::class.java,
            RoomDB.DATABASE_NAME
        ).openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        if (RetrofitService.retrofit == null) {
            RetrofitService.retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return RetrofitService.retrofit as Retrofit
    }

    @Provides
    fun provideShowService(retrofit: Retrofit): ShowService = retrofit.create(
        ShowService::class.java
    )

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(
        MovieService::class.java
    )

    @Provides
    fun provideShowRepository(
        localDataSource: ShowLocalDataSource,
        remoteDataSource: ShowRemoteDataSource
    ): IShowRepository {
        return ShowRepository(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideMovieRepository(
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource
    ): IMovieRepository {
        return MovieRepository(localDataSource, remoteDataSource)
    }
}