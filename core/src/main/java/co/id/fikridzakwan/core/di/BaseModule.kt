package co.id.fikridzakwan.core.di

import co.id.fikridzakwan.data.repository.IMovieRepository
import co.id.fikridzakwan.data.repository.MovieRepository
import co.id.fikridzakwan.data.source.network.MovieApi
import co.id.fikridzakwan.domain.usecase.MovieInteractor
import co.id.fikridzakwan.domain.usecase.MovieUseCase
import org.koin.dsl.module

val baseModule = module {

    single { MovieApi(get()) }

    factory<IMovieRepository> {
        MovieRepository(
            get(),
            get()
        )
    }

    factory<MovieUseCase> {
        MovieInteractor(
            get()
        )
    }
}