import com.android.tools.r8.internal.kt
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers.experimental
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlinx-serialization")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinSerialization)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.krp.whoknows"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.krp.whoknows"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "SUPABASE_URL", "\"${project.findProperty("SUPABASE_URL")}\"")
        buildConfigField("String", "SUPABASE_KEY", "\"${project.findProperty("SUPABASE_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.constraintlayout.compose.android)
    implementation(libs.androidx.compose.testing)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)

    // Extended Icons
    implementation(libs.androidx.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.bundles.koin)

    implementation(libs.androidx.core.splashscreen)

    //ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.slf4j)
    implementation("io.ktor:ktor-client-auth:2.3.12")

    //koin
    implementation(libs.koin.core) // Koin Core
    implementation(libs.koin.android)
//    implementation(libs.koin.android.compose)// Koin for Android

    // Room
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.material.icons.extended)

    // maps compose
    implementation (libs.maps.compose)

    // google map services
    implementation (libs.play.services.maps)
    implementation (libs.play.services.location)

    // google maps utils
    implementation (libs.android.maps.utils)

    implementation (libs.accompanist.insets)
    implementation (libs.exoplayer.core)
    implementation (libs.exoplayer.ui)

//    implementation ("com.github.x3rocode:xblur-compose:[latest_version]")

    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation ("androidx.palette:palette:1.0.0")

    implementation(libs.androidx.compose.animation)

    implementation ("io.github.jan-tennert.supabase:storage-kt:1.1.0")

    implementation("com.airbnb.android:lottie-compose:6.3.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.3-beta")
    implementation ("app.rive:rive-android:9.6.5")

    implementation ("androidx.startup:startup-runtime:1.1.1")

//    implementation ("com.onesignal:OneSignal:[5.0.0-beta, 5.99.99]")

    implementation("io.socket:socket.io-client:2.0.0")


    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-messaging:24.1.1")

    implementation("com.google.firebase:firebase-analytics")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")


    implementation("com.github.bumptech.glide:glide:4.16.0")

}

