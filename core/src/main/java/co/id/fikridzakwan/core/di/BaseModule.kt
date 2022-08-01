package co.id.fikridzakwan.core.di

import co.id.fikridzakwan.core.data.MovieRepository
import co.id.fikridzakwan.core.data.source.network.MovieApi
import co.id.fikridzakwan.core.domain.repository.IMovieRepository
import co.id.fikridzakwan.core.domain.usecase.MovieInteractor
import co.id.fikridzakwan.core.domain.usecase.MovieUseCase
import org.koin.dsl.module

val baseModule = module {

    single { MovieApi(get()) }

    factory<IMovieRepository> { MovieRepository(get(), get()) }

    factory<MovieUseCase> { MovieInteractor(get()) }
}