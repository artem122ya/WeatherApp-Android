package artem122ya.weatherapp.weatherview

import android.location.Location
import artem122ya.weatherapp.api.ServiceProvider
import artem122ya.weatherapp.api.WeatherService
import artem122ya.weatherapp.models.Result
import artem122ya.weatherapp.models.Period
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class WeatherPresenter private constructor(): WeatherContract.Presenter {

    private var weatherView: WeatherContract.View? = null
        set(value){
            field = value
            weatherView?.presenter = this
        }

    private var currentLocation: Location? = null

    override fun start() {
        if (currentLocation == null) {
            if (weatherView?.isLocationPermissionGranted() != true) {
                return
            }
            if (weatherView?.isLocationAvailable() == true) {
                weatherView?.requestLocation()
            }
        }
        loadDaysForecast()
        loadHoursForecast()

    }

    override fun onLocationButtonClicked() {
        if (weatherView?.isLocationPermissionGranted() != true) {
            return
        }
        if (weatherView?.isLocationAvailable() == true) {
            weatherView?.requestLocation()
        } else weatherView?.showLocationNotAvailableError()
    }

    override fun onPermissionRequestResultCallback(isPermissionGranted: Boolean) {
        if (isPermissionGranted) {
            if (weatherView?.isLocationAvailable() == true) {
                weatherView?.requestLocation()
            } else weatherView?.showLocationNotAvailableError()
        } else weatherView?.showNoPermissionErrorMessage()
    }

    override fun onDayClicked(day: Period) {

    }

    override fun setLocation(location: Location?) {
        currentLocation = location
        loadDaysForecast()
        loadHoursForecast()
    }

    override fun loadForecast() {

    }

    private fun loadDaysForecast() = launch(UI)  {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            result = weatherService.getDailyForecast(currentLocation!!.latitude, currentLocation!!.longitude).await()
        } catch (e: Exception) {

        }
        val daysForecastData: List<Period>? = result?.response?.get(0)?.periods
        weatherView?.setDaysForecast(daysForecastData ?: ArrayList())
        daysForecastData?.get(0)?.let {
            weatherView?.setForecast(it)
        }
        weatherView?.setCityName(result?.response?.get(0)?.profile?.tz ?: "")
    }

    private fun loadHoursForecast() = launch(UI) {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            result = weatherService.getHourlyForecast(currentLocation!!.latitude, currentLocation!!.longitude).await()
        } catch (e: Exception) {

        }

        //TODO cryptic code
        val hoursForecastData: List<Period>? = result?.response?.get(0)?.periods
        weatherView?.setHoursForecast(hoursForecastData ?: ArrayList())
        
    }

    companion object {

        private val INSTANCE: WeatherPresenter = WeatherPresenter()

        fun getInstance(view: WeatherContract.View): WeatherPresenter {
            INSTANCE.weatherView = view
            return INSTANCE
        }
    }

}