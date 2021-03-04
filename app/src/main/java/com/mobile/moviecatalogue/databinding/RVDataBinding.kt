package com.mobile.moviecatalogue.databinding

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.mobile.core.presentation.adapter.paging.BindablePagedRVAdapter
import com.mobile.core.presentation.adapter.rv.BindableRVAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("rvData")
fun <T> rvData(rv: RecyclerView, data: List<T>?) {
    data?.let{
        if (rv.adapter is BindableRVAdapter<*>) {
            (rv.adapter as BindableRVAdapter<T>).setData(data)
        }
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("rvData")
fun <T> rvData(rv: RecyclerView, data: PagedList<T>?){
    data?.let {
        if (rv.adapter is BindablePagedRVAdapter<*>) {
            (rv.adapter as BindablePagedRVAdapter<T>).setData(data)
        }
    }
}
