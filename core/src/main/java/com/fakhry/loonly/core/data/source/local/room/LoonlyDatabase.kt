package com.fakhry.loonly.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.utils.DataConverter

@Database(
    entities = [MovieEntity::class,
        MovieWatchlistEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class LoonlyDatabase : RoomDatabase() {

    abstract fun loonlyDao(): LoonlyDao
}