package kotli.common.data.source.database.room

import androidx.room3.RoomDatabase
import androidx.room3.useWriterConnection
import androidx.sqlite.SQLiteDriver
import kotli.common.data.source.database.DatabaseSource
import kotli.common.data.source.database.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotli.common.data.source.database.room.entity.User as UserEntity

expect fun createSQLiteDriver(): SQLiteDriver

/**
 * This class represents a source for accessing the Room database.
 *
 * It provides access to all underlying DAO objects as well.
 */
class RoomSource(
    private val databaseBuilder: RoomDatabase.Builder<RoomDb>,
) : DatabaseSource {

    private val db by lazy {
        databaseBuilder
            .fallbackToDestructiveMigrationOnDowngrade(false)
            .setDriver(createSQLiteDriver())
            .build()
    }

    private val userDao by lazy { db.getUserDao() }

    override suspend fun getUsersLive(): Flow<List<User>> {
        return userDao.getAllAsFlow().map { all -> all.map(::map) }
    }

    override suspend fun getUsers(limit: Int, offset: Int): List<User> {
        return userDao.getAll(limit, offset).map(::map)
    }

    override suspend fun insertUser(firstName: String, lastName: String) {
        userDao.create(UserEntity(firstName = firstName, lastName = lastName))
    }

    override suspend fun deleteUser(id: Long) {
        userDao.delete(UserEntity(id = id))
    }

    override suspend fun getUsersCount(): Long {
        return userDao.count()
    }

    override suspend fun inTx(block: suspend () -> Unit) {
        db.useWriterConnection { block() }
    }

    private fun map(from: UserEntity): User {
        return User(
            id = from.id,
            firstName = from.firstName,
            lastName = from.lastName
        )
    }
}