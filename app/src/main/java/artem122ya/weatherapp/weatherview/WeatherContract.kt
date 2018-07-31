package artem122ya.weatherapp.weatherview

import artem122ya.weatherapp.BasePresenter
import artem122ya.weatherapp.BaseView
import artem122ya.weatherapp.models.Period

interface WeatherContract {

    interface View: BaseView<Presenter> {

        fun setDaysForecast(data: List<Period>)

    }

    interface Presenter: BasePresenter {

        fun onDayClicked(day: Period)

    }

}