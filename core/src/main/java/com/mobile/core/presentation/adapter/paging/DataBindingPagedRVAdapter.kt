package com.mobile.core.presentation.adapter.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mobile.core.presentation.adapter.DataBindingViewHolder
import com.mobile.core.presentation.adapter.listener.BindableListener

abstract class DataBindingPagedRVAdapter<T>(
    private val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>,
    private val clickListener: BindableListener?
) : PagedListAdapter<T, DataBindingViewHolder<T>>(diffCallback),
    BindablePagedRVAdapter<T> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int = layoutId

    override fun setData(data: PagedList<T>) {
        this.submitList(data)
    }
}