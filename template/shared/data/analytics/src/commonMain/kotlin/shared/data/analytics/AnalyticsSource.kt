package shared.data.analytics

import shared.data.common.DataSource

/**
 * Interface for managing analytics events.
 * Provides general methods to log any events in the app.
 */
interface AnalyticsSource : DataSource {

    /**
     * Sets the user ID for tracking analytics events.
     *
     * @param id The user ID to set.
     */
    fun setUserId(id: String?)

    /**
     * Sets a user property for tracking analytics events.
     * The properties set are associated with all subsequent events.
     *
     * @param key The key of the user property.
     * @param value The value of the user property.
     */
    fun setUserProperty(key: String, value: String?)

    /**
     * Logs an error event with the specified error.
     *
     * @param event The name of the error event.
     * @param error The Throwable representing the error.
     */
    fun onError(event: String, error: Throwable)

    /**
     * Logs any custom event with optional parameters.
     *
     * @param event The name of the event.
     * @param params Optional parameters for the event.
     */
    fun onEvent(event: String, params: Map<String, String> = mapOf())

    /**
     * Logs a screen view event with optional parameters.
     *
     * @param screenName The name of the screen.
     * @param params Optional parameters for the screen view event.
     */
    fun onScreenView(screenName: String, params: Map<String, String> = mapOf())

}