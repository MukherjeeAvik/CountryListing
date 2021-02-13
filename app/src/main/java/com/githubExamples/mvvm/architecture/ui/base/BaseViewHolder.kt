package com.githubExamples.mvvm.architecture.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(itemView: ViewBinding) : RecyclerView.ViewHolder(itemView.root) {

    var itemClickCallback: ItemClickedCallback<T>? = null


    abstract fun loadData(receivedData: T, callback: ((item: T) -> Unit)?)


    interface ItemClickedCallback<T> {
        fun onItemClicked(t: T)
    }


}
