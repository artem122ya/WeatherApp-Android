package artem122ya.weatherapp.weatherview.search

import artem122ya.weatherapp.BasePresenter
import artem122ya.weatherapp.BaseView
import artem122ya.weatherapp.models.Loc
import artem122ya.weatherapp.models.Response

interface SearchContract {

    interface View: BaseView<Presenter> {

        fun setSearchResultsData(data: List<Response>, overrideDataSet: Boolean)

        fun showProgressBar()

        fun hideProgressBar()

        fun showLoadingErrorMessage()

        fun onLocationPicked(location: Loc)

    }

    interface Presenter: BasePresenter {

        fun searchPlaces(query: String)

        fun loadMoreResults()

        fun onResultClick(response: Response)
    }

}