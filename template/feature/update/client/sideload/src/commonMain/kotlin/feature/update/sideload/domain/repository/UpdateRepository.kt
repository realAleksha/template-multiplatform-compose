package feature.update.sideload.domain.repository

internal interface UpdateRepository {
    suspend fun download(url: String, onProgress: (Float) -> Unit): String
    suspend fun install(filePath: String)
    suspend fun getUpdatePath(): String?
    suspend fun setUpdatePath(path: String?)
}
