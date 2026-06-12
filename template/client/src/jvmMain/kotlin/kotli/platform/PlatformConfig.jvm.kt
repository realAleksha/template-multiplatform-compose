package kotli.platform

import androidx.room3.Room
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotli.common.data.source.database.DatabaseSource
import kotli.common.data.source.database.room.RoomDb
import kotli.common.data.source.database.room.RoomSource
import kotli.common.data.source.database.sqldelight.SqlDelightSource
import org.koin.dsl.module
import java.io.File

actual val platform = module {
    // {data.database.room}
    single<DatabaseSource> {
        val dbName = "app.db"
        val dbFile = File(System.getProperty("java.io.tmpdir"), dbName)
        val dbBuilder = Room.databaseBuilder<RoomDb>(dbFile.absolutePath)
        RoomSource(dbBuilder)
    }
    // {data.database.room}
    // {data.database.sqldelight}
    single<DatabaseSource> {
        val dbName = "app.db"
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbName}")
        SqlDelightSource(driver)
    }
    // {data.database.sqldelight}
}