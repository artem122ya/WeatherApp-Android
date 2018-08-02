package artem122ya.weatherapp.weatherview.search

import artem122ya.weatherapp.api.ServiceProvider
import artem122ya.weatherapp.api.WeatherService
import artem122ya.weatherapp.models.Response
import artem122ya.weatherapp.models.Result
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class SearchPresenter private constructor(): SearchContract.Presenter {

    private val resultsPerPage = 10

    private var searchView: SearchContract.View? = null
        set(value){
            field = value
            searchView?.presenter = this
        }

    private var page = 0

    private var previousQuery: String = ""

    override fun start() {}

    override fun onResultClick(response: Response) {

    }

    override fun searchPlaces(query: String) {
        var shouldOverrideDataSet = false
        if (query != previousQuery) {
            page = 0
            previousQuery = query
            shouldOverrideDataSet = true
        }
        loadResults(shouldOverrideDataSet)
    }

    override fun loadMoreResults() {
        loadResults(false)
    }

    private fun loadResults(overrideDataSet: Boolean) = launch(UI) {
        searchView?.showProgressBar()
        val weatherService: WeatherService = ServiceProvider.weatherService
        var result: Result? = null
        try {
            result = weatherService.searchPlaces(previousQuery, resultsPerPage, page * resultsPerPage).await()
            page++
        } catch (e: Exception) {
            searchView?.showLoadingErrorMessage()
        }
        searchView?.hideProgressBar()
        searchView?.setSearchResultsData(result?.response ?: ArrayList(), overrideDataSet)
    }

    companion object {

        private val INSTANCE: SearchPresenter = SearchPresenter()

        fun getInstance(view: SearchContract.View): SearchPresenter {
            INSTANCE.searchView = view
            return INSTANCE
        }
    }

}