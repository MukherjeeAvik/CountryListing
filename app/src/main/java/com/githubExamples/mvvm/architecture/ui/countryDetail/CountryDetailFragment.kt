package com.githubExamples.mvvm.architecture.ui.countryDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.githubExamples.mvvm.acrhitecture.databinding.CountryDetailsFragmentBinding
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.MainViewModel
import com.githubExamples.mvvm.architecture.ui.base.BaseFragment
import com.githubExamples.mvvm.architecture.ui.base.ViewModelProviderFactory
import com.githubExamples.mvvm.architecture.utils.INCONSISTENT_VALUE
import com.githubExamples.mvvm.architecture.utils.NOT_AVAIALBLE
import kotlinx.android.synthetic.main.country_details_fragment.*
import javax.inject.Inject

class CountryDetailFragment() : BaseFragment<CountryDetailsFragmentBinding>() {
    lateinit var sharedViewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    override fun getFragmentTag() = TAG

    override fun getLifeCycleObserver() = sharedViewModel

    override fun reloadPage() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel =
                ViewModelProvider(requireActivity(), providerFactory).get(MainViewModel::class.java)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CountryDetailsFragmentBinding
        get() = CountryDetailsFragmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backArrow.setOnClickListener {
            sharedViewModel.registerNavigationRoutes(Routes.GoBack)
        }
        val countryDetailsInfo = arguments?.getParcelable<CountryItem>(COUNTRY_DETAILS)

        countryDetailsInfo?.let { countryDetails ->

            countryName.text = countryDetails.name
            if (countryDetails.latitude == INCONSISTENT_VALUE.toString()) {
                latitudeValue.text = NOT_AVAIALBLE
            } else {
                latitudeValue.text = countryDetails.latitude
            }
            if (countryDetails.longitude == INCONSISTENT_VALUE.toString()) {
                longitudeValue.text = NOT_AVAIALBLE
            } else {
                longitudeValue.text = countryDetails.longitude
            }

            var borders = ""
            countryDetails.borders.forEach {
                borders = "$borders$it, "
            }
            if (borders.isEmpty())
                bordersValues.text = NOT_AVAIALBLE
            else {
                bordersValues.text = borders.substringBeforeLast(",")
            }

            var languages = ""
            countryDetails.languages.forEach {
                languages = "$languages$it, "
            }
            if (languages.isEmpty())
                languagesValues.text = NOT_AVAIALBLE
            else {
                languagesValues.text = languages.substringBeforeLast(",")
            }
        }


    }

    companion object {
        const val TAG = "COUNTRY_DETAILS_FRAGMENT"
        const val COUNTRY_DETAILS = "COUNTRY_DETAILS"
        fun newInstance(countryItem: CountryItem): CountryDetailFragment {

            val args = Bundle()
            args.putParcelable(COUNTRY_DETAILS, countryItem)
            val fragment = CountryDetailFragment()
            fragment.arguments = args
            return fragment
        }

    }
}
