package kotli.template.multiplatform.compose

object Rules {

    // common
    const val BuildGradle = "*build.gradle.kts"
    const val StringsXml = "*/strings.xml"
    const val IndexHtml = "*/index.html"
    const val IosConfig = "*/Config.xcconfig"
    const val AndroidSrcDir = "*/src/androidMain"
    const val WebSrcDir = "*/src/webMain"
    const val JvmSrcDir = "*/src/jvmMain"
    const val JsSrcDir = "*/src/jsMain"
    const val WasmJsSrcDir = "*/src/wasmJsMain"
    const val IosSrcDir = "*/ios*"
    const val GradleProperties = "gradle.properties"

    // root
    const val RootSettingsGradle = "settings.gradle.kts"
    const val RootBuildGradle = "build.gradle.kts"

    // client
    const val Client = "client"
    const val ClientSrc = "${Client}/src"
    const val ClientAssembleDir = "${Client}/assemble"
    const val ClientBuildGradle = "${Client}/build.gradle.kts"
    const val ClientCommonMain = "${ClientSrc}/commonMain"
    const val ClientCommonMainRoot = "${ClientCommonMain}/kotlin/kotli"
    const val ClientAppRoot = "${ClientCommonMainRoot}/app/"
    const val ClientAppConfigKt = "${ClientAppRoot}/AppConfig.kt"
    const val ClientAppViewModelKt = "${ClientAppRoot}/presentation/AppViewModel.kt"
    const val ClientCommon = "${ClientCommonMainRoot}/common"
    const val ClientCommonConfigKt = "${ClientCommon}/CommonConfig.kt"
    const val ClientProguardRulesPro = "${ClientAssembleDir}/proguard-rules.pro"
    const val ClientWebPackConfigDir = "${Client}/webpack.config.d"
    const val ClientSqlDelightConfigJs = "${ClientWebPackConfigDir}/sqljs-config.js"
    const val ClientPlatformConfigKt = "*/PlatformConfig.*.kt"
    const val ClientCommonDatabase = "${ClientCommon}/data/source/database"
    const val ClientApplicationKt = "${ClientSrc}/*/Application.kt"

    // server
    const val ServerDir = "server"
    const val ServerSrc = "${ServerDir}/src"

    // shared -> domain
    const val DomainDir = "shared/domain"
    const val DomainBuildGradle = "${DomainDir}/build.gradle.kts"

    // shared -> data
    const val DataDir = "shared/data"
    const val DataBuildGradle = "${DataDir}/*/build.gradle.kts"
    const val DataCommonDir = "${DataDir}/common"
    const val AnalyticsSource = "*/*AnalyticsSource*.kt"
    const val AnalyticsDir = "${DataDir}/analytics"
    const val CacheSource = "*/*CacheSource*.kt"
    const val CacheSourceDir = "${DataDir}/cache"
    const val SqlDelightSource = "*/*SqlDelightSource*.kt"
    const val SqlDelightDir = "*/sqldelight/*"
    const val RoomSource = "*/*RoomSource*.kt"
    const val SupabaseSource = "*/*Supabase*.kt"
    const val RoomDir = "*/database/room/*"
    const val ConfigSource = "*/*ConfigSource.kt"
    const val ConfigDir = "${DataDir}/config"
    const val PagingSource = "*/*Paging*.kt"
    const val PagingSourceDir = "${DataDir}/paging"
    const val PagingJetpackDir = "${DataDir}/paging-jetpack"
    const val HttpSource = "*/*HttpSource.kt"
    const val HttpDir = "${DataDir}/http"
    const val EncodingDir = "${DataDir}/encoding"
    const val SettingsSource = "*/*SettingsSource.kt"
    const val SettingsDir = "${DataDir}/settings"
    const val DataStoreSource = "*/DataStoreSource*.kt"
    const val DataStoreDir = "${DataDir}/settings-datastore"
    const val EncryptionSource = "*/*EncryptionSource*.kt"
    const val EncryptionDir = "${DataDir}/encryption"
    const val EncryptionKorlibsDir = "${DataDir}/encryption-korlibs"
    const val ExpressionSource = "*/*ExpressionEvaluator*.kt"
    const val ExpressionDir = "${DataDir}/expression"
    const val ExpressionImplDir = "${DataDir}/expression-impl"
    const val AiSource = "*/*AiSource*.kt"

    // shared -> presentation
    const val PresentationDir = "shared/presentation"
    const val PresentationBuildGradle = "${PresentationDir}/build.gradle.kts"
    const val PresentationUiDir = "${PresentationDir}/src/commonMain/kotlin/shared/presentation/ui"
    const val PresentationComponentDir = "${PresentationUiDir}/component"
    const val PresentationComponentFilePicker = "${PresentationComponentDir}/DsFilePicker.kt"
    const val PresentationComponentMarkdown = "${PresentationComponentDir}/DsMarkdown.kt"
    const val PresentationComponentIcon = "${PresentationComponentDir}/DsIcon.kt"
}