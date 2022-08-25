package co.id.fikridzakwan.presentation.di

import co.id.fikridzakwan.presentation.viewmodel.SearchMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchMovieViewModel(get()) }
}