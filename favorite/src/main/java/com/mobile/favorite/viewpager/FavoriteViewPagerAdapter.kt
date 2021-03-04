package com.mobile.favorite.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoriteViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> com.mobile.favorite.ui.ListFragment.newInstance(com.mobile.favorite.ui.ListFragment.TYPE_MOVIE)
            else -> com.mobile.favorite.ui.ListFragment.newInstance(com.mobile.favorite.ui.ListFragment.TYPE_SHOW)
        }
    }
}