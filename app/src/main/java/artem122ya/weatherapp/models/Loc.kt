package artem122ya.weatherapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Loc(
        @SerializedName("long") val long: Double,
        @SerializedName("lat") val lat: Double
): Parcelable