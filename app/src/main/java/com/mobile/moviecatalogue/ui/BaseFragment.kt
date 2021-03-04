package com.mobile.moviecatalogue.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.moviecatalogue.R

abstract class BaseFragment : Fragment() {
    protected open var bottomNavigationViewVisibility = View.VISIBLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
            if (bottomNavigationViewVisibility == View.GONE) return
            this.view?.let {
                val param = it.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(
                    param.leftMargin,
                    param.topMargin,
                    param.rightMargin,
                    resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
                )
                it.layoutParams = param
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
            if (bottomNavigationViewVisibility == View.GONE) return
            this.view?.let {
                val param = it.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(
                    param.leftMargin,
                    param.topMargin,
                    param.rightMargin,
                    resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
                )
                it.layoutParams = param
            }
        }
    }
}