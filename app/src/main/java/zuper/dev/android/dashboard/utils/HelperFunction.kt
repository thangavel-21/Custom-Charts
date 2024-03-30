package zuper.dev.android.dashboard.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object HelperFunction {

    fun getCurrentDate(): String {
        val currentDate = Date()
        val formatter = SimpleDateFormat(Constants.CURRENT_DATE, Locale.getDefault())
        return formatter.format(currentDate)
    }

    fun convertUtcToLocalTime(utcTimeString: String?): String? {
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(Constants.UTC)
        val parsedDate: Date? = utcTimeString?.let { dateFormat.parse(it) }
        val localFormat = SimpleDateFormat(
            Constants.DATE_TIME,
            Locale.getDefault()
        )
        localFormat.timeZone = TimeZone.getDefault()
        return parsedDate?.let { localFormat.format(it).uppercase() }
    }

    fun calculatePercentage(size: Int, totalSize: Int): Int {
        return if (size > 0 && totalSize > 0) {
            size * 100 / totalSize
        } else 0
    }
}