package kotli.common.data.source.database.room

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.TypeConverters
import kotli.common.data.source.database.room.dao.UserDao
import kotli.common.data.source.database.room.entity.User

/**
 * This class represents the Room database for the application.
 */
@Database(
    entities = [
        User::class
    ],
    version = 1
)
@TypeConverters(RoomConverters::class)
@ConstructedBy(RoomDbConstructor::class)
abstract class RoomDb : RoomDatabase() {

    /**
     * Retrieves the DAO (Data Access Object) for interacting with the User entity.
     *
     * @return The UserDao instance.
     */
    abstract fun getUserDao(): UserDao
}