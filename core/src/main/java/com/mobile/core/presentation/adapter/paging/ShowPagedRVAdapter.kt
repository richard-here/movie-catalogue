package com.mobile.core.presentation.adapter.paging

import com.mobile.core.presentation.adapter.diff.ShowOverviewDiffCallback
import com.mobile.core.presentation.adapter.listener.ItemClickListener
import com.mobile.core.domain.model.show.ShowOverview

class ShowPagedRVAdapter(
    layoutId: Int,
    clickListener: ItemClickListener
) : DataBindingPagedRVAdapter<ShowOverview>(layoutId, ShowOverviewDiffCallback(), clickListener)