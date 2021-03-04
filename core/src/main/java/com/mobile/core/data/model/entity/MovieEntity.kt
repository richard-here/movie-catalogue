package com.mobile.core.data.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.core.data.source.local.contract.FavoriteMovieContract
import com.mobile.core.domain.model.Genre

@Entity(tableName = FavoriteMovieContract.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = FavoriteMovieContract.COLUMN_ID)
    val id: Int,
    @ColumnInfo
    val backdropUrl: String,
    @ColumnInfo
    val genres: List<Genre>,
    @ColumnInfo(name = FavoriteMovieContract.COLUMN_TITLE)
    val title: String,
    @ColumnInfo
    val summary: String,
    @ColumnInfo
    val coverUrl: String,
    @ColumnInfo
    val year: Int,
    @ColumnInfo
    val runtime: Int,
    @ColumnInfo
    val rating: Float,
    @ColumnInfo
    val voteCount: Int,
    @ColumnInfo(name = FavoriteMovieContract.COLUMN_INSERTED_AT)
    val insertedAt: Int = System.currentTimeMillis().toInt()
)
