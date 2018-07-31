package artem122ya.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Profile(
        @SerializedName("tz") val tz: String
)