package kotli.common.data.source.database.room

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver

actual fun createSQLiteDriver(): SQLiteDriver {
    return NativeSQLiteDriver()
}