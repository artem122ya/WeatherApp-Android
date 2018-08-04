package artem122ya.weatherapp.api

import artem122ya.weatherapp.models.Result
import artem122ya.weatherapp.util.Constants
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("forecasts/{lat},{lon}?filter=1day&limit=7&client_id=${Constants.CLIENT_ID}&client_secret=${Constants.CLIENT_SECRET}")
    fun getDailyForecast(@Path("lat") latitude: Double, @Path("lon") longitude: Double): Deferred<Result>

    @GET("forecasts/{lat},{lon}?filter=1hr&limit=24&client_id=${Constants.CLIENT_ID}&client_secret=${Constants.CLIENT_SECRET}")
    fun getHourlyForecast(@Path("lat") latitude: Double, @Path("lon") longitude: Double, @Query("skip") skip: Int): Deferred<Result>

    @GET("places/search?client_id=${Constants.CLIENT_ID}&client_secret=${Constants.CLIENT_SECRET}")
    fun searchPlaces(@Query("query") query: String, @Query("limit") limit: Int, @Query("skip") skip: Int): Deferred<Result>

    @GET("/places/closest?client_id=${Constants.CLIENT_ID}&client_secret=${Constants.CLIENT_SECRET}")
    fun getPlace(@Query("p") latAndLon: String): Deferred<Result>
}