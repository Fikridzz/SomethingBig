package co.id.fikridzakwan.somethingbig.di.featuremodule

import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailMovieViewModel(get()) }
}