package co.id.fikridzakwan.somethingbig.di.featuremodule

import co.id.fikridzakwan.somethingbig.presentation.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}