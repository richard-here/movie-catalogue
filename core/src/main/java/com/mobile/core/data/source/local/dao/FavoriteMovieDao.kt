package com.mobile.core.data.source.local.dao

import androidx.paging.DataSource
import androidx.room.*
import com.mobile.core.data.model.entity.MovieEntity
import com.mobile.core.data.source.local.contract.FavoriteMovieContract
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM ${FavoriteMovieContract.TABLE_NAME} ORDER BY ${FavoriteMovieContract.COLUMN_TITLE} ASC")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM ${FavoriteMovieContract.TABLE_NAME} ORDER BY ${FavoriteMovieContract.COLUMN_TITLE} DESC")
    fun getFavoriteMoviesAlphabeticallyDescending(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM ${FavoriteMovieContract.TABLE_NAME} ORDER BY ${FavoriteMovieContract.COLUMN_INSERTED_AT} ASC")
    fun getFavoriteMoviesChronologicallyAscending(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM ${FavoriteMovieContract.TABLE_NAME} ORDER BY ${FavoriteMovieContract.COLUMN_INSERTED_AT} DESC")
    fun getFavoriteMoviesChronologicallyDescending(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM ${FavoriteMovieContract.TABLE_NAME} WHERE ${FavoriteMovieContract.COLUMN_ID} = :id")
    fun getFavoriteMovieById(id: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(favoriteMovie: MovieEntity): Long

    @Query("DELETE FROM ${FavoriteMovieContract.TABLE_NAME} WHERE ${FavoriteMovieContract.COLUMN_ID} = :id")
    fun deleteFavoriteMovie(id: Int): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteMovie(favoriteMovieEntity: MovieEntity)
}