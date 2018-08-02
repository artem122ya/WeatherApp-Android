package artem122ya.weatherapp.util

import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period

val Period.windSpeedMeterPerSecond: Double
    get() = windSpeedKPH * 1000 / 3600

fun Period.getWindDirectionDrawable(): Int? {
    return when(windDir) {
        ("N") -> R.drawable.icon_wind_n
        ("NE") -> R.drawable.icon_wind_ne
        ("SE") -> R.drawable.icon_wind_se
        ("S") -> R.drawable.icon_wind_s
        ("SW") -> R.drawable.icon_wind_sw
        ("W") -> R.drawable.icon_wind_w
        ("NW") -> R.drawable.icon_wind_nw
        else -> null
    }
}