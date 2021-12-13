package co.id.fikridzakwan.somethingbig.di

import co.id.fikridzakwan.somethingbig.data.MovieRepository
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository
}