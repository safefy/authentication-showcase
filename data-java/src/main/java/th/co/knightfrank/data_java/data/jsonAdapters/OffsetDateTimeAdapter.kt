package th.co.knightfrank.data_java.data.jsonAdapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class OffsetDateTimeAdapter {
    @ToJson
    fun toJson(offsetDateTime: OffsetDateTime): String {
        return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    @FromJson
    fun fromJson(offsetDateTimeString: String): OffsetDateTime {
        return OffsetDateTime.parse(offsetDateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }
}