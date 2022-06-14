package co.id.fikridzakwan.somethingbig.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import co.id.fikridzakwan.somethingbig.data.source.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}