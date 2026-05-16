package kotli.common.data.source.database.room

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker

actual fun createSQLiteDriver(): SQLiteDriver {
    return WebWorkerSQLiteDriver(createWorker())
}

val workerUrl: String = js("""new URL("sqlite-web-worker/worker.js", import.meta.url)""")

private fun createWorker() = Worker(workerUrl)