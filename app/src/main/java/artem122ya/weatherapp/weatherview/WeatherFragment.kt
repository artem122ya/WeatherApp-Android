package artem122ya.weatherapp.weatherview


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period
import artem122ya.weatherapp.util.*
import artem122ya.weatherapp.weatherview.search.SearchFragment
import artem122ya.weatherapp.weatherview.search.SearchPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlin.math.roundToInt


class WeatherFragment: Fragment(), WeatherContract.View {

    private val LOCATION_PERMISSION_REQUEST = 1

    override lateinit var presenter: WeatherContract.Presenter

    private val daysForecastAdapter: DaysForecastAdapter = DaysForecastAdapter()
    private val hoursForecastAdapter: HoursForecastAdapter = HoursForecastAdapter()

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daysForecastRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        daysForecastAdapter.onClickListener = {day -> presenter.onDayClicked(day) }
        daysForecastRecyclerView.setHasFixedSize(true)
        daysForecastRecyclerView.adapter = daysForecastAdapter


        hoursForecastRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

        hoursForecastRecyclerView.setHasFixedSize(true)
        hoursForecastRecyclerView.adapter = hoursForecastAdapter

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.context)

        locationButton.setOnClickListener { presenter.onLocationButtonClicked() }

        cityNameTextView.setOnClickListener { openSearchFragment() }

    }


    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.onPermissionRequestResultCallback(true)
                } else {
                    presenter.onPermissionRequestResultCallback(false)
                }
            }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun requestLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    presenter.setLocation(location)
                }
    }

    override fun setCityName(cityName: String) {
        cityNameTextView.text = cityName
    }

    override fun setForecast(forecast: Period) {
        forecast.getWeatherDrawable(COLOR_WHITE)?.let { weatherImageView.setImageResource(it) }
        forecast.getWindDirectionDrawable()?.let { windDirectionIconImageView.setImageResource(it) }
        temperatureTextView.text = getTemperatureTitle(forecast.maxTempC, forecast.minTempC)
        humidityTextView.text = getString(R.string.humidity_percentage_label, forecast.humidity.toInt())
        windSpeedTextView.text = getString(R.string.meters_per_second_label, forecast.windSpeedMeterPerSecond.roundToInt())
    }

    override fun setDaysForecast(data: List<Period>) {
        daysForecastAdapter.daysDataSet = data
    }

    override fun setHoursForecast(data: List<Period>) {
        hoursForecastAdapter.hoursDataSet = data
    }

    override fun isLocationAvailable(): Boolean {
        return  Settings.Secure.LOCATION_MODE_OFF !=
                Settings.Secure.getInt(context!!.contentResolver, Settings.Secure.LOCATION_MODE)
    }

    override fun isLocationPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST)
        } else {
            return true
        }
        return false
    }

    override fun showLocationNotAvailableError() {
        Snackbar.make(activity!!.findViewById(android.R.id.content),
                "Turn on GPS!", Snackbar.LENGTH_LONG).show()

    }

    override fun showNoPermissionErrorMessage() {
        Snackbar.make(activity!!.findViewById(android.R.id.content),
                "Permission needed to access location", Snackbar.LENGTH_LONG).show()
    }

    override fun openSearchFragment() {
        val locationSearchFragment = SearchFragment.newInstance()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, locationSearchFragment)
                .addToBackStack(null)
                .commit()
        SearchPresenter.getInstance(locationSearchFragment)
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}
