package kotli.common.data.source.database.room

import androidx.room3.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Class containing Room type converters for Date objects.
 */
class RoomConverters {

    /**
     * Converts a timestamp value (Long) to a Date object.
     *
     * @param value The timestamp value to convert.
     * @return The corresponding Date object, or null if the input value is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let(Instant::fromEpochMilliseconds)?.toLocalDateTime(TimeZone.UTC)
    }

    /**
     * Converts a Date object to a timestamp value (Long).
     *
     * @param date The Date object to convert.
     * @return The corresponding timestamp value, or null if the input date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.UTC)?.toEpochMilliseconds()
    }

}