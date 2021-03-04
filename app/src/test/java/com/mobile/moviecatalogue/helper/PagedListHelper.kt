package com.mobile.moviecatalogue.helper

import androidx.paging.PagedList
import io.mockk.every
import io.mockk.mockk

object PagedListHelper {
    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mockk<PagedList<T>>()
        every { pagedList[0] } returns list[0]
        every { pagedList.size } returns list.size
        return pagedList
    }
}