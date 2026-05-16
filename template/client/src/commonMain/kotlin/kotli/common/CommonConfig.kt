package kotli.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotli.common.data.source.supabase.SupabaseSource
import org.koin.dsl.module
import shared.data.source.analytics.AnalyticsSource
import shared.data.source.analytics.BasicAnalyticsSource
import shared.data.source.cache.BasicCacheSource
import shared.data.source.cache.CacheSource
import shared.data.source.config.BasicConfigSource
import shared.data.source.config.ConfigSource
import shared.data.source.encryption.EncryptionSource
import shared.data.source.encryption.korlibs.KorlibsEncryptionSource
import shared.data.source.http.HttpSource
import shared.data.source.paging.PagingSource
import shared.data.source.paging.jetpack.JetpackPagingSource
import shared.data.source.settings.SettingsSource
import shared.data.source.settings.datastore.DataStoreSource

fun NavGraphBuilder.common(navController: NavHostController) {}

val common = module {
    single { HttpSource() }
    single<CacheSource> { BasicCacheSource() }
    single<ConfigSource> { BasicConfigSource() }
    single<AnalyticsSource> { BasicAnalyticsSource() }
    single<PagingSource> { JetpackPagingSource() }
    single<EncryptionSource> { KorlibsEncryptionSource() }
    single<SettingsSource> { DataStoreSource() }
    // {supabase}
    single<SupabaseSource> {
        SupabaseSource(
            projectUrl = "https://cacrwyudzpkngprgbmez.supabase.co",
            apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNhY3J3eXVkenBrbmdwcmdibWV6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU1MTcyMjQsImV4cCI6MjA2MTA5MzIyNH0.4CBjHlrFlbvmVqD4w4orqNpQKqmmXRA2QO0vKSilZFo",
            googleClientId = "301620890191-8h9dnml3a25sucrs0ssjmbor6pck4op0.apps.googleusercontent.com"
        )
    }
    // {supabase}
}