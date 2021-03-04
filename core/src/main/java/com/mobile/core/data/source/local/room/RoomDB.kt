package com.mobile.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobile.core.data.model.entity.MovieEntity
import com.mobile.core.data.model.entity.ShowEntity
import com.mobile.core.data.source.local.dao.FavoriteMovieDao
import com.mobile.core.data.source.local.dao.FavoriteShowDao

@Database(
    entities = [MovieEntity::class, ShowEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteShowDao(): FavoriteShowDao

    companion object {
        const val DATABASE_NAME = "database"
    }
}