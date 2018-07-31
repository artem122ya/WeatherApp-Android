package artem122ya.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Response(
        @SerializedName("loc") val loc: Loc,
        @SerializedName("interval") val interval: String,
        @SerializedName("periods") val periods: List<Period>,
        @SerializedName("profile") val profile: Profile
)