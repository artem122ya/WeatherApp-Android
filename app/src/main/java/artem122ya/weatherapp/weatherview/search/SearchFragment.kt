package artem122ya.weatherapp.weatherview.search


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
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
import android.view.inputmethod.InputMethodManager
import artem122ya.weatherapp.R
import artem122ya.weatherapp.models.Loc
import artem122ya.weatherapp.models.Response
import artem122ya.weatherapp.weatherview.LOCATION_BUNDLE_EXTRA
import artem122ya.weatherapp.weatherview.WeatherFragment
import kotlinx.android.synthetic.main.location_search_fragment.*


class SearchFragment : Fragment(), SearchContract.View {
    //TODO text to big
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

        backButton.setOnClickListener {
            hideKeyboard()
            activity!!.supportFragmentManager.popBackStack()
        }

    }



    override fun setSearchResultsData(data: List<Response>, overrideDataSet: Boolean) {
        searchAdapter.setData(data, overrideDataSet)
        isLoading = data.isEmpty()
    }

    override fun onLocationPicked(location: Loc) {
        hideKeyboard()
        val intent = Intent(context, WeatherFragment::class.java)
        intent.putExtra(LOCATION_BUNDLE_EXTRA, location)
        targetFragment!!.onActivityResult(targetRequestCode, RESULT_OK, intent)
        fragmentManager!!.popBackStack()
    }

    override fun showProgressBar() {
        searchAdapter.showProgressBar()
    }

    override fun hideProgressBar() {
        searchAdapter.hideProgressBar()
    }

    override fun showLoadingErrorMessage() {
        activity?.let {
            Snackbar.make(it.findViewById(R.id.contentFrame),
                getString(R.string.search_loading_error_message), Snackbar.LENGTH_LONG).show()}
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity!!.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
