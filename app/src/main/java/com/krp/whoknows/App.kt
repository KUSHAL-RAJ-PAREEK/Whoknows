package com.krp.whoknows

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */
import android.app.Application
import com.krp.whoknows.ktorclient.di.appModule
import com.krp.whoknows.ktorclient.di.ktorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(ktorModule)
            modules(appModule)

        }

    }
}