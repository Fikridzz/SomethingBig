package co.id.fikridzakwan.presentation.di

import co.id.fikridzakwan.presentation.viewmodel.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}