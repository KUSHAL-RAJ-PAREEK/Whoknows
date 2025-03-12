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
        supabaseUrl = "https://dtgatrenwhgxvicpbxre.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR0Z2F0cmVud2hneHZpY3BieHJlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE1ODQ5MTMsImV4cCI6MjA1NzE2MDkxM30.2ZcSpw6klDiHtsg2PIQTh1VQXn5JkgG4NVw0gmivvCY"
    ) {
        install(Storage)
    }
}