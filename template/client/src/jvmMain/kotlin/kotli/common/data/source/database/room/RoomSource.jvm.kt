package kotli.common.data.source.database.room

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun createSQLiteDriver(): SQLiteDriver {
    return BundledSQLiteDriver()
}