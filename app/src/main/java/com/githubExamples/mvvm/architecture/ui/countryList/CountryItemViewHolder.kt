package com.githubExamples.mvvm.architecture.ui.countryList

import android.view.LayoutInflater
import android.view.ViewGroup
import com.githubExamples.mvvm.acrhitecture.R
import com.githubExamples.mvvm.acrhitecture.databinding.CountryItemRowBinding
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.base.BaseViewHolder
import com.githubExamples.mvvm.architecture.utils.INCONSISTENT_VALUE
import com.githubExamples.mvvm.architecture.utils.hide
import com.githubExamples.mvvm.architecture.utils.show

class CountryItemViewHolder(
        private val itemBinding: CountryItemRowBinding) : BaseViewHolder<CountryItem>(itemBinding) {

    override fun loadData(receivedData: CountryItem, callback: ((item: CountryItem) -> Unit)?) {
        itemBinding.run {
            countryNameTv.text = receivedData.name
            countryCapitalTv.text =
                    itemView.context.getString(R.string.capitalText, receivedData.capital)
            if (receivedData.population == INCONSISTENT_VALUE.toString()) {
                countryPopulationTv.hide()
            } else {
                countryPopulationTv.show()
                countryPopulationTv.text =
                        itemView.context.getString(R.string.populationText, receivedData.population)
            }

            var currencies = ""
            receivedData.currency.forEach {
                currencies = "$currencies$it, "
            }
            if (currencies.isEmpty())
                countryCurrencyTv.hide()
            else {
                countryCurrencyTv.show()
                countryCurrencyTv.text =
                        itemView.context.getString(R.string.currenciesText, currencies.substringBeforeLast(","))
            }

            detailsPageCTA.setOnClickListener {
                callback?.invoke(receivedData)
            }
        }

    }

    companion object {
        inline fun create(
                parent: ViewGroup,
                crossinline block: (inflater: LayoutInflater, container: ViewGroup, attach: Boolean) -> CountryItemRowBinding
        ) = CountryItemViewHolder(block(LayoutInflater.from(parent.context), parent, false))
    }

}
