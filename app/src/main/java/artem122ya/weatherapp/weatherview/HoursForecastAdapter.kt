package artem122ya.weatherapp.weatherview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period
import artem122ya.weatherapp.util.*
import kotlinx.android.synthetic.main.hour_forecast_list_item.view.*


class HoursForecastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var hoursDataSet: List<Period> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.hour_forecast_list_item, parent, false)
        return HourViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HourViewHolder) {
            val forecast: Period = hoursDataSet[position]
            forecast.getWeatherDrawable(COLOR_WHITE)?.let {
                holder.weatherImageView.setImageResource(it)
            }
            holder.temperatureTextView.text = (forecast.avgTempC.toInt().toString() + "°")
            holder.hourLabelTextView.text = holder.hourLabelTextView.context
                    .getString(R.string.hours_label, getHourTitle(forecast.dateTimeISO).toInt())
        }
    }

    override fun getItemCount(): Int = hoursDataSet.size


    inner class HourViewHolder(view: View): RecyclerView.ViewHolder(view){

        val hourLabelTextView: TextView = view.hourTextView
        val temperatureTextView: TextView = view.hourTemperatureTextView
        val weatherImageView: ImageView = view.hourWeatherIconImageView

    }

}