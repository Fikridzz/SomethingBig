package co.id.fikridzakwan.core.di

import androidx.room.Room
import co.id.fikridzakwan.core.data.source.room.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }
}