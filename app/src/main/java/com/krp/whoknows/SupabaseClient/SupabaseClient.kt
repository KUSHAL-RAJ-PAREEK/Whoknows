package com.krp.whoknows.SupabaseClient

import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth

/**
 * Created by KUSHAL RAJ PAREEK on 10,March,2025
 */

@OptIn(SupabaseInternal::class)
fun supabaseClient(): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = "SUPABASE_URL",
        supabaseKey = "API_KEY"
    ) {
        install(Storage)
    }
}
