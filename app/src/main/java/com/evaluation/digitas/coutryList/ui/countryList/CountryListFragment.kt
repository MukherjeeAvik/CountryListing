package com.evaluation.digitas.coutryList.ui.countryList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evaluation.digitas.coutryList.R
import com.evaluation.digitas.coutryList.di.qualifiers.FilterZone
import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.ui.MainViewModel
import com.evaluation.digitas.coutryList.ui.ViewStates
import com.evaluation.digitas.coutryList.ui.base.BaseFragment
import com.evaluation.digitas.coutryList.ui.base.BaseViewHolder
import com.evaluation.digitas.coutryList.utils.hide
import com.evaluation.digitas.coutryList.utils.show
import com.evaluation.digitas.coutryList.utils.showAsPer
import kotlinx.android.synthetic.main.country_listing_fragment.*
import com.evaluation.digitas.coutryList.ui.base.ViewModelProviderFactory
import javax.inject.Inject

class CountryListFragment : BaseFragment() {

    lateinit var sharedViewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var countryListAdapter: CountryListAdapter

    @Inject
    @FilterZone
    lateinit var region: String

    override fun getLayoutId() = R.layout.country_listing_fragment

    override fun getFragmentTag() = TAG

    override fun getLifeCycleObserver() = sharedViewModel

    override fun reloadPage() {
        sharedViewModel.disposeOngoingOperationIfAny()
        sharedViewModel.getListOfCountries()
        pullToRefresh.isRefreshing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel =
            ViewModelProvider(requireActivity(), providerFactory).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewSates()
        sharedViewModel.getCurrentViewState()
        setUpView()

    }

    private fun setUpView() {
        pullToRefresh.setOnRefreshListener {
            reloadPage()
        }
        retryButton.setOnClickListener {
            reloadPage()
        }
        regionHeader.text = "Countries from $region"
    }

    companion object {
        const val TAG = "COUNTRY_LIST_FRAGMENT"
        fun newInstance(): CountryListFragment {

            val args = Bundle()
            val fragment = CountryListFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private fun observeViewSates() {
        sharedViewModel.observeViewStates().observe(viewLifecycleOwner, Observer { viewStates ->
            when (viewStates) {
                is ViewStates.CountryListStates.ShowLoading -> {
                    progressBar.show()
                    pullToRefresh.hide()
                    noDataAvailable.hide()
                }
                is ViewStates.CountryListStates.ShowContent -> {
                    progressBar.showAsPer(viewStates.isLoading)
                    pullToRefresh.showAsPer(viewStates.showList)
                    if (viewStates.hasError) {
                        notifyUserThroughMessage(viewStates.errorMessage)
                    } else {
                        removeErrors()
                    }
                    if (viewStates.showList && viewStates.countryList.isNotEmpty()) {
                        pullToRefresh.show()
                        noDataAvailable.hide()
                        setUpAdapter(viewStates.countryList)
                    } else {
                        pullToRefresh.hide()
                        noDataAvailable.show()
                    }

                }
            }
        })

    }

    private fun setUpAdapter(listOfCountries: List<CountryItem>) {
        countryListAdapter.registerForCallbacks(object :
            BaseViewHolder.ItemClickedCallback<CountryItem> {
            override fun onItemClicked(t: CountryItem) {
                sharedViewModel.navigateToDetailsPage(t)
            }

        })
        countryListAdapter.addAll(listOfCountries)
        countryListRv.adapter = countryListAdapter

    }


}
