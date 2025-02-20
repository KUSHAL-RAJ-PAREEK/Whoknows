package com.krp.whoknows.ktorclient.di

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import com.krp.whoknows.Appui.userInfo.CreateUserViewModel
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneAuthViewModel
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.JWTViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ktorModule = module {
    single { provideHttpClient() }
    single { KtorClient() }
}
val appModule: Module = module {
    viewModel { PhoneAuthViewModel() }
    viewModel{ OTPVerificationViewModel() }
    viewModel{ JWTViewModel(androidApplication())}
    viewModel{InfoViewModel()}
    viewModel{ CreateUserViewModel() }
}

fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 5000
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
        }

        install(DefaultRequest) {
            url {
                host = "java-spring-boot-production-2c58.up.railway.app"
                protocol = URLProtocol.HTTPS
            }
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}