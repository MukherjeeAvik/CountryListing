package com.evaluation.digitas.coutryList.navigation

import androidx.fragment.app.FragmentManager
import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.ui.base.BaseNavigator
import com.evaluation.digitas.coutryList.ui.countryDetail.CountryDetailFragment
import com.evaluation.digitas.coutryList.ui.countryList.CountryListFragment
import javax.inject.Inject

class MainNavigator @Inject constructor(fragmentManager: FragmentManager) :
    BaseNavigator(fragmentManager) {

    fun openListingPage(container: Int, addToBackStack: Boolean) {

        var fragment = findFragment(CountryListFragment.TAG)
        if (fragment == null) {
            fragment = CountryListFragment.newInstance()
        }

        showFragmentWithAnimation(
            fragment,
            CountryListFragment.TAG,
            addToBackStack,
            container
        )

    }

    fun openDetailsPage(container: Int, addToBackStack: Boolean, countryItem: CountryItem) {

        var fragment = findFragment(CountryDetailFragment.TAG)
        if (fragment == null) {
            fragment = CountryDetailFragment.newInstance(countryItem)
        }

        showFragmentWithAnimation(
            fragment,
            CountryDetailFragment.TAG,
            addToBackStack,
            container
        )

    }

    fun isDetailsPageShowing(): Boolean {
        return getTopFragmentTag() == "COUNTRY_DETAILS_FRAGMENT"
    }


}
