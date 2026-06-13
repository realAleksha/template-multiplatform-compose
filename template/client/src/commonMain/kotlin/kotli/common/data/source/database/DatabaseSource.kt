package kotli.common.data.source.database

import kotli.common.data.source.database.model.User
import kotlinx.coroutines.flow.Flow
import shared.data.common.DataSource

interface DatabaseSource : DataSource {

    suspend fun inTx(block: suspend () -> Unit)

    suspend fun getUsersCount(): Long
    suspend fun getUsersLive(): Flow<List<User>>
    suspend fun getUsers(limit: Int, offset: Int): List<User>
    suspend fun insertUser(firstName: String, lastName: String)
    suspend fun deleteUser(id: Long)
}