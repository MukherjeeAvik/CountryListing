package com.evaluation.digitas.coutryList.ui.countryList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evaluation.digitas.coutryList.R
import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.ui.base.BaseViewHolder
import com.evaluation.digitas.coutryList.utils.INCONSISTENT_VALUE
import com.evaluation.digitas.coutryList.utils.hide
import com.evaluation.digitas.coutryList.utils.show
import kotlinx.android.synthetic.main.country_item_row.view.*

class CountryItemViewHolder(itemView: View) : BaseViewHolder<CountryItem>(itemView) {
    override fun loadData(receivedData: CountryItem) {

        itemView.countryNameTv.text = receivedData.name
        itemView.countryCapitalTv.text =
            itemView.context.getString(R.string.capitalText, receivedData.capital)
        if (receivedData.population == INCONSISTENT_VALUE.toString()) {
            itemView.countryPopulationTv.hide()
        } else {
            itemView.countryPopulationTv.show()
            itemView.countryPopulationTv.text =
                itemView.context.getString(R.string.populationText, receivedData.population)
        }

        var currencies = ""
        receivedData.currency.forEach {
            currencies = "$currencies$it, "
        }
        if (currencies.isEmpty())
            itemView.countryCurrencyTv.hide()
        else {
            itemView.countryCurrencyTv.show()
            itemView.countryCurrencyTv.text =
                itemView.context.getString(R.string.currenciesText, currencies.substringBeforeLast(","))
        }

        itemView.detailsPageCTA.setOnClickListener {
            itemClickCallback?.onItemClicked(receivedData)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            callBack: ItemClickedCallback<CountryItem>?
        ): CountryItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.country_item_row, parent, false)
            return CountryItemViewHolder(view).also { it.itemClickCallback = callBack }
        }
    }

}
