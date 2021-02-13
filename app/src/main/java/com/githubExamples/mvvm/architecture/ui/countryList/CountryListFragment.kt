package com.githubExamples.mvvm.architecture.ui.countryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.githubExamples.mvvm.acrhitecture.R
import com.githubExamples.mvvm.acrhitecture.databinding.CountryListingFragmentBinding
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.CountryListStates
import com.githubExamples.mvvm.architecture.ui.MainViewModel
import com.githubExamples.mvvm.architecture.ui.base.BaseFragment
import com.githubExamples.mvvm.architecture.ui.base.ViewModelProviderFactory
import com.githubExamples.mvvm.architecture.utils.hide
import com.githubExamples.mvvm.architecture.utils.show
import com.githubExamples.mvvm.architecture.utils.showAsPer
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CountryListFragment : BaseFragment<CountryListingFragmentBinding>() {

    lateinit var sharedViewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var countryListAdapter: Lazy<CountryListAdapter>

    @Inject
    @FilterZone
    lateinit var region: String

    override fun getFragmentTag() = TAG

    override fun getLifeCycleObserver() = sharedViewModel

    override fun reloadPage() {
        sharedViewModel.disposeOngoingOperationIfAny()
        sharedViewModel.getListOfCountries()
        binding.pullToRefresh.isRefreshing = false
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
        binding.pullToRefresh.setOnRefreshListener {
            reloadPage()
        }
        binding.retryButton.setOnClickListener {
            reloadPage()
        }
        binding.regionHeader.text = requireContext().getString(R.string.regionText, region)
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
                        binding.apply {
                            progressBar.show()
                            pullToRefresh.hide()
                            noDataAvailable.hide()
                        }

                    }
                    is CountryListStates.ShowContent -> {
                        binding.apply {
                            progressBar.showAsPer(viewStates.isLoading)
                            pullToRefresh.showAsPer(viewStates.showList)
                        }
                        if (viewStates.hasError) {
                            notifyUserThroughMessage(viewStates.errorMessage)
                        } else {
                            removeErrors()
                        }
                        if (viewStates.showList && viewStates.countryList.isNotEmpty()) {
                            binding.apply {
                                pullToRefresh.show()
                                noDataAvailable.hide()
                            }
                            setUpAdapter(viewStates.countryList)
                        } else {
                            binding.apply {
                                pullToRefresh.hide()
                                noDataAvailable.show()
                            }

                        }

                    }
                }

            }
        }


    }

    private fun setUpAdapter(listOfCountries: List<CountryItem>) {

        countryListAdapter.get().registerCallback {
            sharedViewModel.registerNavigationRoutes(Routes.GotoDetailsPage(it))
        }
        countryListAdapter.get().addAll(listOfCountries)
        binding.countryListRv.adapter = countryListAdapter.get()

    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CountryListingFragmentBinding
        get() = CountryListingFragmentBinding::inflate


}
