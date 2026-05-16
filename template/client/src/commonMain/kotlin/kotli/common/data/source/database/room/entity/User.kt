package kotli.common.data.source.database.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlin.time.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * This class represents a User entity in the database.
 * <p>
 * This entity is provided as an example to define custom entities in Room.
 * Developers should create their own entities tailored to their application's requirements.
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "first_name", index = true, collate = ColumnInfo.NOCASE)
    var firstName: String? = null,
    @ColumnInfo(name = "last_name", index = true, collate = ColumnInfo.NOCASE)
    var lastName: String? = null,
    @ColumnInfo(name = "created")
    var created: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
)