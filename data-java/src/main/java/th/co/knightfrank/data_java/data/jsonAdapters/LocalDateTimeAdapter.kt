package th.co.knightfrank.data_java.data.jsonAdapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @ToJson
    fun toJson(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @FromJson
    fun fromJson(localDateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}