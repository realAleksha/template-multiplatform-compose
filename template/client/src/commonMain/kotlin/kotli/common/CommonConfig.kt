package kotli.common

import kotli.common.data.source.supabase.SupabaseSource
import org.koin.dsl.module
import shared.data.analytics.AnalyticsSource
import shared.data.analytics.BasicAnalyticsSource
import shared.data.cache.BasicCacheSource
import shared.data.cache.CacheSource
import shared.data.config.BasicConfigSource
import shared.data.config.ConfigSource
import shared.data.encryption.EncryptionSource
import shared.data.encryption.korlibs.KorlibsEncryptionSource
import shared.data.http.HttpSource
import shared.data.paging.PagingSource
import shared.data.paging.jetpack.JetpackPagingSource
import shared.data.settings.SettingsSource
import shared.data.settings.datastore.DataStoreSource

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