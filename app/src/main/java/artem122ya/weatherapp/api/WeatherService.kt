package artem122ya.weatherapp.api

import artem122ya.weatherapp.models.Forecast
import artem122ya.weatherapp.util.Constants
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

interface WeatherService {

    @GET("forecasts/seattle,wa?filter=1day&limit=7&client_id=${Constants.CLIENT_ID}&client_secret=${Constants.CLIENT_SECRET}")
    fun getDailyForecast(): Deferred<Forecast>

}