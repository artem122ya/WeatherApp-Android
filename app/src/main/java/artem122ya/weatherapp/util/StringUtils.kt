package artem122ya.weatherapp.util

import org.joda.time.DateTime



fun getDayOfWeekTitle(isoDate: String): String {
    val date = DateTime(isoDate)
    return date.toString("EE").toUpperCase()
}

fun getHourTitle(isoDate: String): String {
    val date = DateTime(isoDate)
    return date.toString("k")
}

fun getDateLabel(isoDate: String): String {
    val date = DateTime(isoDate)
    return date.toString("EE").toUpperCase() + date.toString(", dd MMMMM")
}