package artem122ya.weatherapp.weatherview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import artem122ya.weatherapp.R

class WeatherActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)

        if (supportFragmentManager.findFragmentById(R.id.contentFrame) == null) {
            val weatherFragment = WeatherFragment.newInstance()
            WeatherPresenter.getInstance(weatherFragment)
            supportFragmentManager.beginTransaction().add(R.id.contentFrame, weatherFragment).commit()
        }
    }
}
