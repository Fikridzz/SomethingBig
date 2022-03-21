package co.id.fikridzakwan.somethingbig.utils

import android.app.Application
import co.id.fikridzakwan.somethingbig.di.baseModule
import co.id.fikridzakwan.somethingbig.di.featuremodule.detailModule
import co.id.fikridzakwan.somethingbig.di.featuremodule.moreModule
import co.id.fikridzakwan.somethingbig.di.featuremodule.movieModule
import co.id.fikridzakwan.somethingbig.di.featuremodule.searchModule
import co.id.fikridzakwan.somethingbig.di.networkModule
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
                    baseModule,
                    movieModule,
                    detailModule,
                    moreModule,
                    searchModule
                )
            )
        }
    }
}