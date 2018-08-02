package artem122ya.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Place(
        @SerializedName("name") val name: String,
        @SerializedName("state") val state: String,
        @SerializedName("stateFull") val stateFull: String,
        @SerializedName("country") val country: String,
        @SerializedName("countryFull") val countryFull: String,
        @SerializedName("region") val region: String,
        @SerializedName("regionFull") val regionFull: String,
        @SerializedName("continent") val continent: String,
        @SerializedName("continentFull") val continentFull: String
)