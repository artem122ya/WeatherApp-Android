package artem122ya.weatherapp.weatherview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import artem122ya.weatherapp.R
import artem122ya.weatherapp.util.replaceFragmentInActivity

class WeatherActivity: AppCompatActivity() {

    private lateinit var weatherPresenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)

        val weatherFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as WeatherFragment? ?: WeatherFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        weatherPresenter = WeatherPresenter.getInstance(weatherFragment)
    }
}
