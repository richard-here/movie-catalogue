package com.mobile.moviecatalogue.databinding

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobile.core.utils.TypedValueUtils
import com.mobile.moviecatalogue.R

@BindingAdapter("fabIcon")
fun fabIcon(view: FloatingActionButton, isFavorited: Boolean) {
    val colorSecondary = TypedValueUtils.resolveThemeColor(view, R.attr.colorSecondary)
    val colorOnSecondary = TypedValueUtils.resolveThemeColor(view, R.attr.colorOnSecondary)
    when (isFavorited) {
        true -> {
            view.setImageDrawable(
                ContextCompat.getDrawable(view.context, R.drawable.svg_favorite)
            )
            view.setColorFilter(colorSecondary)
            view.backgroundTintList = ColorStateList.valueOf(colorOnSecondary)
        }
        else -> {
            view.setImageDrawable(
                ContextCompat.getDrawable(view.context, R.drawable.svg_unfavorite)
            )
            view.setColorFilter(colorOnSecondary)
            view.backgroundTintList = ColorStateList.valueOf(colorSecondary)
        }
    }
}

@BindingAdapter("showFab")
fun showFab(view: FloatingActionButton, showFab: Boolean) {
    when (showFab) {
        false -> view.hide()
        true -> view.show()
    }
}