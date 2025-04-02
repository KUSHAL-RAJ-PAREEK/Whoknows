package com.krp.whoknows.ktorclient.di

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import androidx.room.Room
import com.krp.whoknows.Appui.Chat.presentation.ChatViewModel
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.CancelMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.CheckMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.CreateMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.MatchUserViewModel
import com.krp.whoknows.Appui.Profile.presentation.EditProfileViewModel
import com.krp.whoknows.Appui.Profile.presentation.ImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserViewModel
import com.krp.whoknows.Appui.userInfo.CreateUserViewModel
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneAuthViewModel
import com.krp.whoknows.SupabaseClient.supabaseClient
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.DataBase
import com.krp.whoknows.roomdb.ImageRepository
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
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
    single<SupabaseClient> { supabaseClient() }
    single {
        Room.databaseBuilder(
            get(),
            DataBase::class.java,
            "userDB"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<DataBase>().dao() }
    single { UserRepository(get()) }
    single { ImageRepository(get()) }
    single { ImageViewModel(get()) }
    single{MainImageViewModel(get())}
    single { MatchUserViewModel(get()) }
}
val appModule: Module = module {
    viewModel { PhoneAuthViewModel() }
    viewModel{ OTPVerificationViewModel() }
    viewModel{ JWTViewModel(androidApplication())}
    viewModel{InfoViewModel()}
    viewModel{ CreateUserViewModel() }
    viewModel { GreetingViewModel(get(), get()) }
    viewModel{ProfileViewModel(get())}
    viewModel{EditProfileViewModel()}
    viewModel{ProfileDetailViewModel()}
    viewModel{UpdateUserViewModel()}
    viewModel{UpdateMatchViewModel()}
    viewModel{CheckMatchViewModel()}
    viewModel{CreateMatchViewModel()}
    viewModel{CancelMatchViewModel()}
    viewModel{ChatViewModel()}
    viewModel{ChatViewModel()}

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
                host = "whoknows-jaqw.onrender.com"
                protocol = URLProtocol.HTTPS
            }
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}