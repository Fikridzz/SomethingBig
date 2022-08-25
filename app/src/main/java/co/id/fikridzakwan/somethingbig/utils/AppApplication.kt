package co.id.fikridzakwan.somethingbig.utils

import android.app.Application
import co.id.fikridzakwan.core.di.baseModule
import co.id.fikridzakwan.core.di.databaseModule
import co.id.fikridzakwan.core.di.networkModule
import co.id.fikridzakwan.presentation.di.detailModule
import co.id.fikridzakwan.presentation.di.favoriteModule
import co.id.fikridzakwan.presentation.di.moreModule
import co.id.fikridzakwan.presentation.di.searchModule
import co.id.fikridzakwan.presentation.featuremodule.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    baseModule,
                    movieModule,
                    favoriteModule,
                    detailModule,
                    moreModule,
                    searchModule
                )
            )
        }
    }
}