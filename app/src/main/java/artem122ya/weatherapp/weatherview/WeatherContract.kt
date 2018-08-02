package artem122ya.weatherapp.weatherview

import android.location.Location
import artem122ya.weatherapp.BasePresenter
import artem122ya.weatherapp.BaseView
import artem122ya.weatherapp.models.Period

interface WeatherContract {

    interface View: BaseView<Presenter> {

        fun setCityName(cityName: String)

        fun setForecast(forecast: Period)

        fun setDaysForecast(data: List<Period>)

        fun setHoursForecast(data: List<Period>)

        fun requestLocation()

        fun isLocationAvailable(): Boolean

        fun isLocationPermissionGranted(): Boolean

        fun showLocationNotAvailableError()

        fun showNoPermissionErrorMessage()

        fun openSearchFragment()

    }

    interface Presenter: BasePresenter {

        fun onDayClicked(day: Period)

        fun onLocationButtonClicked()

        fun setLocation(location: Location?)

        fun loadForecast()

        fun onPermissionRequestResultCallback(isPermissionGranted: Boolean)
    }

}