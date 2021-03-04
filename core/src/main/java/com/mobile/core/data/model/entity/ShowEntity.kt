package com.mobile.core.data.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.core.data.source.local.contract.FavoriteShowContract
import com.mobile.core.domain.model.Genre

@Entity(tableName = FavoriteShowContract.TABLE_NAME)
data class ShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = FavoriteShowContract.COLUMN_ID)
    val id: Int,
    @ColumnInfo
    val backdropUrl: String,
    @ColumnInfo
    val runtime: Int,
    @ColumnInfo
    val firstAired: String,
    @ColumnInfo
    val genres: List<Genre>,
    @ColumnInfo
    val returningSeries: Boolean,
    @ColumnInfo(name = FavoriteShowContract.COLUMN_TITLE)
    val title: String,
    @ColumnInfo
    val episodes: Int,
    @ColumnInfo
    val seasons: Int,
    @ColumnInfo
    val summary: String,
    @ColumnInfo
    val coverUrl: String,
    @ColumnInfo
    val rating: Float,
    @ColumnInfo
    val voteCount: Int,
    @ColumnInfo(name = FavoriteShowContract.COLUMN_INSERTED_AT)
    val insertedAt: Int = System.currentTimeMillis().toInt()
)

