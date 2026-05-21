package feature.update.sideload

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import shared.data.source.http.HttpSource

class DefaultSideloadUpdateStateResolver(
    private val metadataUrl: String,
    private val httpSource: HttpSource,
    private val currentVersionCode: Int
) : SideloadUpdateStateResolver {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun resolve(): SideloadUpdateState {
        return try {
            val response = httpSource.client.get(metadataUrl).bodyAsText()
            val metadata = json.decodeFromString<UpdateMetadata>(response)
            if (metadata.versionCode > currentVersionCode) {
                if (metadata.force) {
                    SideloadUpdateState.Force(metadata.url)
                } else {
                    SideloadUpdateState.Optional(metadata.url)
                }
            } else {
                SideloadUpdateState.None
            }
        } catch (e: Exception) {
            SideloadUpdateState.Error("Failed to check for updates: ${e.message}")
        }
    }

    @Serializable
    private data class UpdateMetadata(
        val force: Boolean = false,
        val versionCode: Int,
        val url: String,
    )
}