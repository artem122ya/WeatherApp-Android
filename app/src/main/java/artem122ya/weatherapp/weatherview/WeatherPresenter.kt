package artem122ya.weatherapp.weatherview

import artem122ya.weatherapp.api.ServiceProvider
import artem122ya.weatherapp.api.WeatherService
import artem122ya.weatherapp.models.Forecast
import artem122ya.weatherapp.models.Period
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class WeatherPresenter private constructor(): WeatherContract.Presenter {

    private var weatherView: WeatherContract.View? = null
        set(value){
            field = value
            weatherView?.presenter = this
        }


    override fun start() {
        loadForecast()
    }

    override fun onDayClicked(day: Period) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadForecast() = launch(UI)  {
        val weatherService: WeatherService = ServiceProvider.weatherService
        var forecast: Forecast? = null
        try {
            forecast = weatherService.getDailyForecast().await()
        } catch (e: Exception) {

        }
        val daysForecastData: List<Period>? = forecast?.response?.get(0)?.periods
        weatherView?.setDaysForecast(daysForecastData ?: ArrayList())

    }

    companion object {

        private val INSTANCE: WeatherPresenter = WeatherPresenter()

        fun getInstance(view: WeatherContract.View): WeatherPresenter {
            INSTANCE.weatherView = view
            return INSTANCE
        }
    }

}