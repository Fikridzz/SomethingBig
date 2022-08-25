package co.id.fikridzakwan.presentation.di

import co.id.fikridzakwan.presentation.viewmodel.DetailMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailMovieViewModel(get()) }
}