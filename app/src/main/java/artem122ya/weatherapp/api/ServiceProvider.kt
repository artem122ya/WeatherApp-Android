package artem122ya.weatherapp.api


import artem122ya.weatherapp.util.RETROFIT

class ServiceProvider private constructor() {
    companion object {
        val weatherService: WeatherService = RETROFIT.create(WeatherService::class.java)
    }
}