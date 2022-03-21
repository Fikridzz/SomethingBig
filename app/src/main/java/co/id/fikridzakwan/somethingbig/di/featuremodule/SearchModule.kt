package co.id.fikridzakwan.somethingbig.di.featuremodule

import co.id.fikridzakwan.somethingbig.presentation.search.SearchMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchMovieViewModel(get()) }
}