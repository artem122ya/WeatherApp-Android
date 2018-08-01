package artem122ya.weatherapp.util

import android.content.Context
import artem122ya.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*


fun getTemperatureTitle(maxTemp: Double, minTemp: Double): String {
    return "${maxTemp.toInt()}°/${minTemp.toInt()}°"
}

fun getDayOfWeekTitle(timestamp: Int, context: Context): String {
    val sdf = SimpleDateFormat("EEEE", Locale.US)
    val date = Date(timestamp.toLong() * 1000)
    val day = sdf.format(date)
    return when(day) {
        ("Monday") -> context.getString(R.string.monday_label)
        ("Tuesday") -> context.getString(R.string.tuesday_label)
        ("Wednesday") -> context.getString(R.string.wednesday_label)
        ("Thursday") -> context.getString(R.string.thursday_label)
        ("Friday") -> context.getString(R.string.friday_label)
        ("Saturday") -> context.getString(R.string.saturday_label)
        ("Sunday") -> context.getString(R.string.sunday_label)
        else -> ""
    }
}