package co.id.fikridzakwan.somethingbig.di

import co.id.fikridzakwan.somethingbig.data.MovieRepository
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApiClient
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieInteractor
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import org.koin.dsl.module

val baseModule = module {

    single { MovieApi(get()) }

    factory<IMovieRepository> { MovieRepository(get()) }

    factory<MovieUseCase> { MovieInteractor(get()) }
}