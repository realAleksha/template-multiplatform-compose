package shared.data.http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.BrowserUserAgent
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import shared.data.common.DataSource

/**
 * A data source implementation that utilizes OkHttp as the underlying HTTP client.
 *
 * @param retries The number of retries to attempt in case of network errors.
 * @param timeout The timeout value for network operations, in milliseconds.
 */
class HttpSource(
    private val retries: Int = 0,
    private val timeout: Long = 30_000L,
    private val config: HttpClientConfig<*>.() -> Unit = {}
) : DataSource {

    /**
     * The HTTP client instance used for making network requests.
     * Configured with content negotiation, logging, timeouts, and retry policies.
     * 
     * For more details, see: https://ktor.io/docs/client-create-multiplatform-application.html
     */
    val client by lazy {
        HttpClient {
            config(this)
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
            install(WebSockets)
            install(HttpRedirect)
            install(HttpTimeout) {
                requestTimeoutMillis = timeout
                connectTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            if (retries > 0) {
                install(HttpRequestRetry) {
                    retryOnServerErrors(retries)
                    retryOnException(retries, true)
                }
            }
            BrowserUserAgent()
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }
}
