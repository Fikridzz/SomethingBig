package co.id.fikridzakwan.core.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import co.id.fikridzakwan.core.data.source.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}