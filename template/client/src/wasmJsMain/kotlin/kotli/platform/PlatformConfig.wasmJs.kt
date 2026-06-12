package kotli.platform

import app.cash.sqldelight.driver.worker.WebWorkerDriver
import kotli.common.data.source.database.DatabaseSource
import kotli.common.data.source.database.sqldelight.SqlDelightSource
import org.koin.dsl.module
import org.w3c.dom.Worker

val workerUrl: String = js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")

actual val platform = module {
    // {data.database.sqldelight}
    single<DatabaseSource> {
        val driver = WebWorkerDriver(
            Worker(workerUrl)
        )
        SqlDelightSource(driver)
    }
    // {data.database.sqldelight}
}