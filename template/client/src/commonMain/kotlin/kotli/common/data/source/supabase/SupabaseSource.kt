package kotli.common.data.source.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.appleNativeLogin
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import shared.data.common.DataSource

class SupabaseSource(
    private val apiKey: String,
    private val projectUrl: String,
    private val googleClientId: String?
) : DataSource {

    val client by lazy {
        createSupabaseClient(
            supabaseUrl = projectUrl,
            supabaseKey = apiKey
        ) {
            install(Auth) {}
            install(Postgrest) {}
            install(Storage) {}
            install(Realtime) {}
            install(Functions) {}
            install(ComposeAuth) {
                if (googleClientId != null) {
                    googleNativeLogin(googleClientId)
                }
                appleNativeLogin()
            }
        }
    }
}