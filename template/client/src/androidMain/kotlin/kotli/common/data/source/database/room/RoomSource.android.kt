package my.app.common.data.source.database.room

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver

actual fun createSQLiteDriver(): SQLiteDriver {
    return AndroidSQLiteDriver()
}