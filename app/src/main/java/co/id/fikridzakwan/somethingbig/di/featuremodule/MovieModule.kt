package co.id.fikridzakwan.somethingbig.di.featuremodule

import co.id.fikridzakwan.somethingbig.presentation.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    viewModel { MovieViewModel(get()) }
}