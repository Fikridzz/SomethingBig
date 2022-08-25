package co.id.fikridzakwan.presentation.featuremodule

import co.id.fikridzakwan.presentation.viewmodel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    viewModel { MovieViewModel(get()) }
}