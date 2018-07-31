package artem122ya.weatherapp.weatherview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Period
import kotlinx.android.synthetic.main.day_forecast_list_item.view.*


class DaysForecastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClickListener: (Period) -> Unit = {}

    var daysDataSet: List<Period> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.day_forecast_list_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DayViewHolder) {
            holder.temperatureTextView.text = daysDataSet[position].avgTempC.toString()
        }
    }

    override fun getItemCount(): Int = daysDataSet.size


    inner class DayViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        val temperatureTextView: TextView = view.findViewById(R.id.dayTemperature)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onClickListener(daysDataSet[adapterPosition])
            }
        }
    }

}