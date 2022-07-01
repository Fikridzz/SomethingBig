package co.id.fikridzakwan.somethingbig.di

import co.id.fikridzakwan.somethingbig.presentation.more.MoreMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moreModule = module {
    viewModel { MoreMovieViewModel(get()) }
}