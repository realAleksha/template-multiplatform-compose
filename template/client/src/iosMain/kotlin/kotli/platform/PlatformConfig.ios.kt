package kotli.platform

import androidx.room3.Room
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotli.common.data.source.database.DatabaseSource
import kotli.common.data.source.database.room.RoomSource
import kotli.common.data.source.database.sqldelight.SqlDelightDb
import kotli.common.data.source.database.sqldelight.SqlDelightSource
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory
import kotli.common.data.source.database.room.RoomDb as RoomDatabase

actual val platform = module {
    // {data.database.room}
    single<DatabaseSource> {
        val dbName = "app.db"
        val dbFilePath = NSHomeDirectory() + "/$dbName"
        val dbBuilder = Room.databaseBuilder<RoomDatabase>(dbFilePath)
        RoomSource(dbBuilder)
    }
    // {data.database.room}
    // {data.database.sqldelight}
    single<DatabaseSource> {
        val dbName = "app.db"
        val driver = NativeSqliteDriver(SqlDelightDb.Schema.synchronous(), dbName)
        SqlDelightSource(driver)
    }
    // {data.database.sqldelight}
}