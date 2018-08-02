package artem122ya.weatherapp.weatherview.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Response
import kotlinx.android.synthetic.main.progressbar_list_item.view.*
import kotlinx.android.synthetic.main.search_list_item.view.*

class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_ITEM = 0
    private val VIEW_PROGRESSBAR = 1

    var responseDataSet: MutableList<Response?> = mutableListOf()
    var onClickListener: (Response) -> Unit = {}

    var isProgressBarVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_PROGRESSBAR) {
            return ProgressBarViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.progressbar_list_item, parent, false))
        } else {
            return SearchResultViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_list_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchResultViewHolder) {
            responseDataSet[position]?.place?.let {
                holder.cityAndCountryTextView.text = it.name + ", " + it.country
            }
        }
    }

    override fun getItemCount(): Int = responseDataSet.size

    override fun getItemViewType(position: Int): Int {
        return if (responseDataSet[position] == null) VIEW_PROGRESSBAR else VIEW_ITEM
    }

    fun showProgressBar() {
        if (!isProgressBarVisible) {
            isProgressBarVisible = true
            responseDataSet.add(null)
        }
    }

    fun hideProgressBar()  {
        if (isProgressBarVisible) {
            responseDataSet.removeAt(responseDataSet.size - 1)
            isProgressBarVisible = false
        }
    }

    fun setData(data: List<Response>, override: Boolean) {
        if (isProgressBarVisible) hideProgressBar()
        if (override) responseDataSet.clear()
        responseDataSet.addAll(data)
    }

    inner class SearchResultViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val cityAndCountryTextView: TextView = view.cityAndCountryTextView
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                responseDataSet[adapterPosition]?.let(onClickListener)
            }
        }
    }

    class ProgressBarViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.searchProgressBar
    }

}