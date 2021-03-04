package com.mobile.core.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mobile.core.presentation.adapter.listener.BindableListener
import com.mobile.core.presentation.adapter.DataBindingViewHolder

abstract class DataBindingRVAdapter<T>(
    private val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>,
    private val clickListener: BindableListener?
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback),
    BindableRVAdapter<T> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int = layoutId

    override fun setData(data: List<T>) {
        this.submitList(data)
    }
}