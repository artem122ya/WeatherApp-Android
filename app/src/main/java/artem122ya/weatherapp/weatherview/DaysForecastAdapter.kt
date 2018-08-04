package artem122ya.weatherapp.weatherview

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period
import artem122ya.weatherapp.util.COLOR_BLACK
import artem122ya.weatherapp.util.COLOR_PRIMARY
import artem122ya.weatherapp.util.getDayOfWeekTitle
import artem122ya.weatherapp.util.getWeatherDrawable
import kotlinx.android.synthetic.main.day_forecast_list_item.view.*


class DaysForecastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClickListener: (Int) -> Unit = {}

    var daysDataSet: List<Period> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.day_forecast_list_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DayViewHolder) {
            val context = holder.dayOfWeekTextView.context
            var drawableColor = COLOR_BLACK
            if (position == selectedItem) {
                holder.dayItemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentWhite))
                holder.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                holder.temperatureTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                drawableColor = COLOR_PRIMARY
            } else {
                holder.dayItemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                holder.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                holder.temperatureTextView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
            }

            val forecast: Period = daysDataSet[position]
            forecast.getWeatherDrawable(drawableColor)?.let {
                holder.weatherImageView.setImageResource(it)
            }
            holder.temperatureTextView.text = holder.temperatureTextView.context
                    .getString(R.string.temperature_label, forecast.maxTempC.toInt(), forecast.minTempC.toInt())
            holder.dayOfWeekTextView.text = getDayOfWeekTitle(forecast.dateTimeISO)
        }
    }

    override fun getItemCount(): Int = daysDataSet.size


    inner class DayViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        val dayOfWeekTextView: TextView = view.dayOfWeekTextView
        val temperatureTextView: TextView = view.dayTemperatureTextView
        val weatherImageView: ImageView = view.weatherIconImageView
        val dayItemLayout: LinearLayout = view.dayItemLayout
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                if (adapterPosition != selectedItem) {
                    val previousSelectedItem = selectedItem
                    selectedItem = adapterPosition
                    notifyItemChanged(previousSelectedItem)
                    notifyItemChanged(adapterPosition)
                    onClickListener(adapterPosition)
                }
            }
        }
    }

}