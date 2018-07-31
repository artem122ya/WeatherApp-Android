package artem122ya.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Loc(
        @SerializedName("long") val long: Double,
        @SerializedName("lat") val lat: Double
)