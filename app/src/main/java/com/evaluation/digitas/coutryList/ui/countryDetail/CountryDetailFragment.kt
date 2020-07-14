package com.evaluation.digitas.coutryList.ui.countryDetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.evaluation.digitas.coutryList.R
import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.ui.MainViewModel
import com.evaluation.digitas.coutryList.ui.base.BaseFragment
import com.evaluation.digitas.coutryList.utils.INCONSISTENT_VALUE
import com.evaluation.digitas.coutryList.utils.NOT_AVAIALBLE
import kotlinx.android.synthetic.main.country_details_fragment.*
import com.evaluation.digitas.coutryList.ui.base.ViewModelProviderFactory
import javax.inject.Inject

class CountryDetailFragment : BaseFragment() {
    lateinit var sharedViewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun getLayoutId() = R.layout.country_details_fragment

    override fun getFragmentTag() = TAG

    override fun getLifeCycleObserver() = sharedViewModel

    override fun reloadPage() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel =
            ViewModelProvider(requireActivity(), providerFactory).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backArrow.setOnClickListener {
            sharedViewModel.goBack()
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
