package artem122ya.weatherapp.weatherview.search


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Response
import kotlinx.android.synthetic.main.location_search_fragment.*


class SearchFragment : Fragment(), SearchContract.View {

    override lateinit var presenter: SearchContract.Presenter

    private val searchAdapter = SearchAdapter()
    private var isLoading = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.location_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        searchResultsRecyclerView.layoutManager = layoutManager
        searchResultsRecyclerView.setHasFixedSize(true)
        searchResultsRecyclerView.adapter = searchAdapter
        searchResultsRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val last = layoutManager.findLastVisibleItemPosition()
                if (dy > 0 && !isLoading && last + 2 >= searchAdapter.itemCount) {
                    isLoading = true
                    presenter.loadMoreResults()
                }
            }
        })
        searchAdapter.onClickListener = {response -> presenter.onResultClick(response)}

        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.searchPlaces(s.toString())
            }

        })

        retainInstance = true
    }

    override fun setSearchResultsData(data: List<Response>, overrideDataSet: Boolean) {
        searchAdapter.setData(data, overrideDataSet)
        isLoading = data.isEmpty()
    }

    override fun showProgressBar() {
        searchAdapter.showProgressBar()
    }

    override fun hideProgressBar() {
        searchAdapter.hideProgressBar()
    }

    override fun showLoadingErrorMessage() {
        Snackbar.make(activity!!.findViewById(android.R.id.content),
                getString(R.string.search_loading_error_message), Snackbar.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

}
