package com.krp.whoknows

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */
import android.app.Application
import com.google.firebase.FirebaseApp
import com.krp.whoknows.ktorclient.di.appModule
import com.krp.whoknows.ktorclient.di.ktorModule
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {



    override fun onCreate() {
        super.onCreate()

        DynamicToast.Config.getInstance()
            .setDisableIcon(false)  // Ensure icons are not disabled
            .setErrorIcon(null)  // Prevent default tinting behavior
            .setSuccessIcon(null)
            .setWarningIcon(null)
            .setTintIcon(false)
            .apply();

        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(ktorModule)
            modules(appModule)
        }

    }
}