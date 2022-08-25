package co.id.fikridzakwan.presentation.di

import co.id.fikridzakwan.presentation.viewmodel.MoreMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moreModule = module {
    viewModel { MoreMovieViewModel(get()) }
}