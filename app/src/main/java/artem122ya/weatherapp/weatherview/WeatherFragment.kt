package artem122ya.weatherapp.weatherview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period
import kotlinx.android.synthetic.main.weather_fragment.*


class WeatherFragment: Fragment(), WeatherContract.View {

    override lateinit var presenter: WeatherContract.Presenter

    private val daysForecastAdapter: DaysForecastAdapter = DaysForecastAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.weather_fragment, container, false)
    //todo root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val daysLayoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        daysForecastRecyclerView.layoutManager = daysLayoutManager

        daysForecastAdapter.onClickListener = {day -> presenter.onDayClicked(day) }
        daysForecastRecyclerView.setHasFixedSize(true)
        daysForecastRecyclerView.adapter = daysForecastAdapter

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setDaysForecast(data: List<Period>) {
        daysForecastAdapter.daysDataSet = data
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}
