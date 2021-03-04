package com.mobile.core.data.source.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.core.data.model.entity.ShowEntity
import com.mobile.core.data.source.local.contract.FavoriteShowContract
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowDao {
    @Query("SELECT * FROM ${FavoriteShowContract.TABLE_NAME} ORDER BY ${FavoriteShowContract.COLUMN_TITLE} ASC")
    fun getFavoriteShows(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM ${FavoriteShowContract.TABLE_NAME} ORDER BY ${FavoriteShowContract.COLUMN_TITLE} DESC")
    fun getFavoriteShowsAlphabeticallyDescending(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM ${FavoriteShowContract.TABLE_NAME} ORDER BY ${FavoriteShowContract.COLUMN_INSERTED_AT} ASC")
    fun getFavoriteShowsChronologicallyAscending(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM ${FavoriteShowContract.TABLE_NAME} ORDER BY ${FavoriteShowContract.COLUMN_INSERTED_AT} DESC")
    fun getFavoriteShowsChronologicallyDescending(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM ${FavoriteShowContract.TABLE_NAME} WHERE ${FavoriteShowContract.COLUMN_ID} = :id")
    fun getFavoriteShowById(id: Int): Flow<ShowEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteShow(favoriteShow: ShowEntity): Long

    @Query("DELETE FROM ${FavoriteShowContract.TABLE_NAME} WHERE ${FavoriteShowContract.COLUMN_ID} = :id")
    fun deleteFavoriteShow(id: Int): Int
}