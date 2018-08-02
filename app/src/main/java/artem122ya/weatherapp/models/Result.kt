package artem122ya.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("success") val success: Boolean,
    @SerializedName("error") val error: Any?,
    @SerializedName("response") val response: List<Response>
)