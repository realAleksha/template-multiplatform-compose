package shared.data.common.misc

import kotlinx.coroutines.CancellationException

/**
 * Checks if this throwable or any of its causes is a [CancellationException].
 *
 * @return true if this throwable or any of its causes is a [CancellationException], false otherwise.
 */
fun Throwable.isCancellationException(): Boolean {
    var exception: Throwable? = this
    while (exception != null && exception !is CancellationException) {
        if (exception == exception.cause) return false
        exception = exception.cause
    }
    return exception is CancellationException
}
