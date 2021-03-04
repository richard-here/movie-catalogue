package com.mobile.core.presentation.adapter.paging

import androidx.paging.PagedList

interface BindablePagedRVAdapter<T> {
    fun setData(data: PagedList<T>)
}