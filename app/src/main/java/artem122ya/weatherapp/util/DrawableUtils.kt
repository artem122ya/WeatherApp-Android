package artem122ya.weatherapp.util

import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period

const val COLOR_WHITE: String = "_white"
const val COLOR_BLACK: String = "_black"
const val COLOR_PRIMARY: String = "_primary"

private val BRIGHT_ICON_NAME: String = "bright"
private val CLOUDY_ICON_NAME: String = "cloudy"
private val RAIN_ICON_NAME: String = "rain"
private val SHOWER_ICON_NAME: String = "shower"
private val THUNDER_ICON_NAME: String = "thunder"

private val sunnyCodes: Set<String> = setOf("CL", "FW", "IC", "UP")
private val cloudyCodes: Set<String> = setOf("SC", "BK", "OV", "BD", "BN", "BR", "F", "FR", "H", "IF", "IP", "K", "VA", "ZF")
private val rainCodes: Set<String> = setOf("A", "BS", "BY", "L", "R", "RW", "RS", "SI", "WM", "S", "SW", "ZL", "ZR", "ZY")
private val thunderCodes: Set<String> = setOf("T", "WP")


fun Period.getWeatherDrawable(color: String): Int? {
    if (!(color == COLOR_WHITE || color == COLOR_BLACK || color == COLOR_PRIMARY)) {
        throw RuntimeException("Invalid color value")
    }

    var drawableName: String = "ic_"
    drawableName += if (isDay) "day_" else "night_"

    var weatherConditions: String = getWeatherConditionsIconName(weatherPrimaryCoded)
    if (weatherConditions == "") return null

    drawableName += weatherConditions + color

    return weatherDrawablesMap[drawableName]
}

private fun getWeatherConditionsIconName(weatherCoded: String): String {
    val weather: String = weatherCoded.substringAfterLast(":")
    val intensity: String = weatherCoded.substringAfter(":").substringBefore(":")
    var weatherIconName = ""
    if (sunnyCodes.contains(weather)) {
        weatherIconName = BRIGHT_ICON_NAME
    } else if (cloudyCodes.contains(weather)) {
        weatherIconName = CLOUDY_ICON_NAME
    } else if (thunderCodes.contains(weather)) {
        weatherIconName = THUNDER_ICON_NAME
    } else if (rainCodes.contains(weather)) {
        if (intensity == "H" || intensity == "WH") {
            weatherIconName = SHOWER_ICON_NAME
        } else weatherIconName = RAIN_ICON_NAME
    }

    return weatherIconName
}

private val weatherDrawablesMap: Map<String, Int> = mapOf(
        "ic_day_bright_black" to  R.drawable.ic_day_bright_black,
        "ic_day_bright_primary" to  R.drawable.ic_day_bright_primary,
        "ic_day_bright_white" to  R.drawable.ic_day_bright_white,
        "ic_day_cloudy_black" to  R.drawable.ic_day_cloudy_black,
        "ic_day_cloudy_primary" to  R.drawable.ic_day_cloudy_primary,
        "ic_day_cloudy_white" to  R.drawable.ic_day_cloudy_white,
        "ic_day_rain_black" to  R.drawable.ic_day_rain_black,
        "ic_day_rain_primary" to  R.drawable.ic_day_rain_primary,
        "ic_day_rain_white" to  R.drawable.ic_day_rain_white,
        "ic_day_shower_black" to  R.drawable.ic_day_shower_black,
        "ic_day_shower_primary" to  R.drawable.ic_day_shower_primary,
        "ic_day_shower_white" to  R.drawable.ic_day_shower_white,
        "ic_day_thunder_black" to  R.drawable.ic_day_thunder_black,
        "ic_day_thunder_primary" to  R.drawable.ic_day_thunder_primary,
        "ic_day_thunder_white" to  R.drawable.ic_day_thunder_white,

        "ic_night_bright_black" to  R.drawable.ic_night_bright_black,
        "ic_night_bright_primary" to  R.drawable.ic_night_bright_primary,
        "ic_night_bright_white" to  R.drawable.ic_night_bright_white,
        "ic_night_cloudy_black" to  R.drawable.ic_night_cloudy_black,
        "ic_night_cloudy_primary" to  R.drawable.ic_night_cloudy_primary,
        "ic_night_cloudy_white" to  R.drawable.ic_night_cloudy_white,
        "ic_night_rain_black" to  R.drawable.ic_night_rain_black,
        "ic_night_rain_primary" to  R.drawable.ic_night_rain_primary,
        "ic_night_rain_white" to  R.drawable.ic_night_rain_white,
        "ic_night_shower_black" to  R.drawable.ic_night_shower_black,
        "ic_night_shower_primary" to  R.drawable.ic_night_shower_primary,
        "ic_night_shower_white" to  R.drawable.ic_night_shower_white,
        "ic_night_thunder_black" to  R.drawable.ic_night_thunder_black,
        "ic_night_thunder_primary" to  R.drawable.ic_night_thunder_primary,
        "ic_night_thunder_white" to  R.drawable.ic_night_thunder_white
        )

