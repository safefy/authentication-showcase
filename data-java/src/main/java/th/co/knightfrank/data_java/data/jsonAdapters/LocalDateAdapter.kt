package th.co.knightfrank.data_java.data.jsonAdapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class LocalDateAdapter {
    @ToJson
    fun toJson(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @FromJson
    fun fromJson(localDateString: String): LocalDate {
        return LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
