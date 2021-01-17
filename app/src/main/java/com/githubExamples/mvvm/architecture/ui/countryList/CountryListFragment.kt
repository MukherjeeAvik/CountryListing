package com.githubExamples.mvvm.architecture.ui.countryList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.githubExamples.mvvm.acrhitecture.R
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.CountryListStates
import com.githubExamples.mvvm.architecture.ui.MainViewModel
import com.githubExamples.mvvm.architecture.ui.base.BaseFragment
import com.githubExamples.mvvm.architecture.ui.base.BaseViewHolder
import com.githubExamples.mvvm.architecture.utils.hide
import com.githubExamples.mvvm.architecture.utils.show
import com.githubExamples.mvvm.architecture.utils.showAsPer
import kotlinx.android.synthetic.main.country_listing_fragment.*
import com.githubExamples.mvvm.architecture.ui.base.ViewModelProviderFactory
import kotlinx.coroutines.flow.collect
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
        sharedViewModel.getListOfCountries()
        setUpView()

    }

    private fun setUpView() {
        pullToRefresh.setOnRefreshListener {
            reloadPage()
        }
        retryButton.setOnClickListener {
            reloadPage()
        }
        regionHeader.text = requireContext().getString(R.string.regionText, region)
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

        lifecycleScope.launchWhenCreated {
            sharedViewModel.observeViewStates().collect { viewStates ->
                when (viewStates) {
                    is CountryListStates.ShowLoading -> {
                        progressBar.show()
                        pullToRefresh.hide()
                        noDataAvailable.hide()
                    }
                    is CountryListStates.ShowContent -> {
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

            }
        }


    }

    private fun setUpAdapter(listOfCountries: List<CountryItem>) {
        countryListAdapter.registerForCallbacks(object :
            BaseViewHolder.ItemClickedCallback<CountryItem> {
            override fun onItemClicked(t: CountryItem) {
                sharedViewModel.registerNavigationRoutes(Routes.GotoDetailsPage(t))
            }

        })
        countryListAdapter.addAll(listOfCountries)
        countryListRv.adapter = countryListAdapter

    }


}
