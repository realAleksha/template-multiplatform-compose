package kotli.platform

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.room3.Room
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kotli.Application
import kotli.common.data.source.database.DatabaseSource
import kotli.common.data.source.database.room.RoomDb
import kotli.common.data.source.database.room.RoomSource
import kotli.common.data.source.database.sqldelight.SqlDelightDb
import kotli.common.data.source.database.sqldelight.SqlDelightSource
import org.koin.dsl.module

actual fun NavGraphBuilder.platform(navController: NavHostController) {
}

actual val platform = module {
    // {data.database.room}
    single<DatabaseSource> {
        val dbName = "app.db"
        val context = Application.ref
        val dbFile = context.getDatabasePath(dbName)
        val dbBuilder = Room.databaseBuilder<RoomDb>(context, dbFile.absolutePath)
        RoomSource(dbBuilder)
    }
    // {data.database.room}
    // {data.database.sqldelight}
    single<DatabaseSource> {
        val dbName = "app.db"
        val context = Application.ref
        val driver = AndroidSqliteDriver(SqlDelightDb.Schema.synchronous(), context, dbName)
        SqlDelightSource(driver)
    }
    // {data.database.sqldelight}
}