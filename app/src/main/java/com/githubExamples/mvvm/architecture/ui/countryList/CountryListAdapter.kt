package com.githubExamples.mvvm.architecture.ui.countryList

import android.view.ViewGroup
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.base.BaseAdapter
import com.githubExamples.mvvm.architecture.ui.base.BaseViewHolder
import javax.inject.Inject

class CountryListAdapter @Inject constructor() :
    BaseAdapter<CountryItem, CountryItemViewHolder>() {

    private var callback_: BaseViewHolder.ItemClickedCallback<CountryItem>? = null
    fun registerForCallbacks(callback: BaseViewHolder.ItemClickedCallback<CountryItem>?) {
        callback_ = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        return CountryItemViewHolder.create(parent, callback_)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.loadData(list[position])
    }

    override fun getItemCount() = listSize
}
