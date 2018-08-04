package artem122ya.weatherapp.weatherview

import artem122ya.weatherapp.api.ServiceProvider
import artem122ya.weatherapp.api.WeatherService
import artem122ya.weatherapp.models.Loc
import artem122ya.weatherapp.models.Period
import artem122ya.weatherapp.models.Result
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class WeatherPresenter private constructor(): WeatherContract.Presenter {

    private var weatherView: WeatherContract.View? = null
        set(value){
            field = value
            weatherView?.presenter = this
        }

    private var currentLocation: Loc? = null

    private var daysForecastData: List<Period>? = null

    override fun start() {
        if (currentLocation == null) {
            if (weatherView?.isLocationPermissionGranted() != true) {
                return
            }
            if (weatherView?.isLocationAvailable() == true) {
                weatherView?.requestLocation()
            }
        }
        loadDaysForecast(0)
        loadHoursForecast(0)
        loadCityName()
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

    override fun onDayClicked(selectedDay: Int) {
        val dayForecast = daysForecastData?.get(selectedDay)
        if (dayForecast != null) {
            weatherView?.setForecast(dayForecast)
        } else {
            loadDaysForecast(selectedDay)
        }
        loadHoursForecast(selectedDay)
    }

    override fun setLocation(location: Loc) {
        currentLocation = location
        loadDaysForecast(0)
        loadHoursForecast(0)
        loadCityName()
    }

    private fun loadDaysForecast(selectedDay: Int) = launch(UI)  {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            result = weatherService.getDailyForecast(currentLocation!!.lat, currentLocation!!.long).await()
        } catch (e: Exception) {

        }
        val daysForecastResult: List<Period>? = result?.response?.get(0)?.periods
        if (daysForecastResult != null) {
            daysForecastData = daysForecastResult
            weatherView?.setDaysForecast(daysForecastResult)
            weatherView?.setForecast(daysForecastResult[selectedDay])
        }


    }

    private fun loadHoursForecast(selectedDay: Int) = launch(UI) {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            result = weatherService.getHourlyForecast(currentLocation!!.lat, currentLocation!!.long, selectedDay * 24).await()
        } catch (e: Exception) {

        }

        //TODO cryptic code
        val hoursForecastData: List<Period>? = result?.response?.get(0)?.periods
        weatherView?.setHoursForecast(hoursForecastData ?: ArrayList())
        
    }

    private fun loadCityName() = launch(UI) {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            currentLocation?.let {
                result = weatherService.getPlace(it.lat.toString() + "," + it.long.toString()).await()
            }
        } catch (e: Exception) {
            e.message
        }
        weatherView?.setCityName(result?.response?.get(0)?.place?.name ?: "")
    }

    companion object {

        private val INSTANCE: WeatherPresenter = WeatherPresenter()

        fun getInstance(view: WeatherContract.View): WeatherPresenter {
            INSTANCE.weatherView = view
            return INSTANCE
        }
    }

}