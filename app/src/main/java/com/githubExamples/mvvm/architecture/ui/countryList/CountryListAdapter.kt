package com.githubExamples.mvvm.architecture.ui.countryList

import android.view.ViewGroup
import com.githubExamples.mvvm.acrhitecture.databinding.CountryItemRowBinding
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.base.BaseAdapter
import javax.inject.Inject

class CountryListAdapter @Inject constructor() :
        BaseAdapter<CountryItem, CountryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        return CountryItemViewHolder.create(parent, CountryItemRowBinding::inflate)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.loadData(list[position], _callback)
    }

    override fun getItemCount() = listSize
}
