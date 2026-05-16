package kotli.common.data.source.database.room.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotli.common.data.source.database.room.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Represents a DAO (Data Access Object) for interacting with the [User] domain.
 *
 * This interface serves as an example of a typical DAO.
 *
 * For more information on the anatomy of a DAO, refer to:
 * [https://developer.android.com/training/data-storage/room/accessing-data#kotlin]
 */
@Dao
interface UserDao {

    @Insert
    suspend fun create(user: User): Long

    @Update
    suspend fun update(vararg users: User)

    @Delete
    suspend fun delete(vararg user: User)

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun get(id: Long): User?

    @Query("SELECT * FROM user ORDER BY id DESC")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit:Int, offset:Int): List<User>

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllAsFlow(): Flow<List<User>>

    @Query("SELECT COUNT(*) FROM user")
    suspend fun count(): Long

}